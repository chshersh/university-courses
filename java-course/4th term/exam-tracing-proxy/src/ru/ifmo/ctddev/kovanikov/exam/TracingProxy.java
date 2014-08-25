package ru.ifmo.ctddev.kovanikov.exam;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TracingProxy {
	private final Object traceObj;
	private final Class<?>[] interfaces;
	private final long depth;
	
	/**
	 * Creates object that implements all interfaces of another
	 * object by another object.
	 * 
	 * @param traceObj as an object that wraps and which interfaces are implemented
	 * @param depth as the number of wraps
	 */
	
	public TracingProxy(Object traceObj, long depth) {
		this.traceObj = traceObj;
		this.interfaces = traceObj.getClass().getInterfaces();
		this.depth = depth;
	}
	
	/**
	 * Tells that object is a TracingProxy.
	 * 
	 * @return string representation of TracingProxy
	 */
	@Override
	public String toString() {
		return "The object " + traceObj + " is TracingProxy with depth " + depth;
	}
	
	/**
	 * Invokes method by its name, depth and arguments
	 * with record-keeping of <code>depth</code> executions.
	 * 
	 * @param methodName as itself
	 * @param params as arguments of of method by <code>methodName</code>
	 * @return result of invoked interface method
	 * @throws IllegalArgumentException if method wasn't found or method is void
	 */
	public Object invoke(String methodName, Object[] params) {
		try {
			// getting classes of arguments
			Class<?>[] paramsClasses = new Class<?>[params.length];
			for (int i = 0; i < params.length; i++) {
				paramsClasses[i] = params[i].getClass();
			}
			
			// searching in implemented interfaces for a method
			for (int i = 0; i < interfaces.length; ++i) {
				Method[] interfaceMethods = interfaces[i].getMethods();
				for (Method method : interfaceMethods) {
					if (!method.getName().equals(methodName) 
							|| method.getReturnType().equals(Void.TYPE)) {
						continue;
					}
					
					// checking for correct arguments
					boolean fits = true;
					final Class<?>[] methodParamsClasses = method.getParameterTypes();
					
					for (int j = 0; j < methodParamsClasses.length && fits; ++j) {
						fits &= methodParamsClasses[i].isAssignableFrom(paramsClasses[i]);
					}
					
					// if we can call method than return value of its result
					if (fits) {
						method.setAccessible(true);
						final Object result = method.invoke(this.traceObj, params);
						if (result == null) {
							continue;
						}
						if (this.depth > 0) {
							return new TracingProxy(result, this.depth - 1);
						}
						return result;
					}
				}
			}
		} catch (InvocationTargetException e) {
			System.out.println(e.getTargetException().getMessage());
		} catch (IllegalAccessException e) {
			System.err.println("Method " + methodName + " is not accessible");
		}
		throw new IllegalArgumentException("No such methods or method is a void");
	}
	
	public static void main(String[] args) {
		// input example: className depth methodName ...arguments
		// java.util.HashMap 1 size
		if (args.length < 3) {
			System.err.println("Not enough arguments!");
			return;
		}
		
		try {
			Class<?> cls = Class.forName(args[0]);
			Object invoked = cls.newInstance();
			
			TracingProxy tp = new TracingProxy(invoked, Long.parseLong(args[1]));
			
			Object[] params = new Object[args.length - 3];
			for (int i = 3; i < args.length; ++i) {
				params[i - 3] = args[i];
			}
			
			Object res = tp.invoke(args[2], params);
			System.out.println(res);
		} catch (ClassNotFoundException e) {
			System.err.println("No such class: " + args[0]);
		} catch (IllegalAccessException e) {
			System.err.println("Have no access to default constructor");
		} catch (InstantiationException e) {
			System.err.println("Cannot create an instance of object: " + args[0]);
		}
	}
}
