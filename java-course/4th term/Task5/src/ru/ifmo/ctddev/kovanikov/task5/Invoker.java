package ru.ifmo.ctddev.kovanikov.task5;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Invoker {

	public void test(String a, String b) {
		
	}
	public static void execute(String className , String methodName, Object... args) {
		Class<?> clazz = null;
		try {
			clazz = Class.forName(className);

			int mod = clazz.getModifiers();
			if (Modifier.isAbstract(mod)) {
				System.out.println("Class " + className + " is abstract");
				return;
			}

			Class<?>[] argParams = new Class<?>[args.length];
			for (int i = 0; i < args.length; i++) {
				argParams[i] = args[i].getClass();
			}

			final Object inst = clazz.newInstance();
			boolean invoked = false;
			Set<List<Class<?>>> paramTypes = new HashSet<List<Class<?>>>();

			while (clazz != null) {
				final Method[] methods = clazz.getMethods();

				for (Method met : methods) {
					if (met.getName().equals(methodName)) {
						boolean fits = true;
						Class<?>[] metParams = met.getParameterTypes();
						List<Class<?>> metParamsList = Arrays.asList(metParams);
						if (!paramTypes.add(metParamsList)) {
							continue;
						}

						int lastVar = 0;
						if (metParams.length != argParams.length) {
							if (met.isVarArgs()) {
								lastVar = 1;
								final int len = metParams.length - 1;
								fits = (len == argParams.length);

								for (int i = len; i < argParams.length; ++i) {
									fits &= metParams[len]
											.getComponentType()
											.isAssignableFrom(argParams[i]);
								}
							} else {
								fits = false;
							}
						}

						for (int i = 0; i < metParams.length - lastVar && fits; ++i) {
							fits &= metParams[i].isAssignableFrom(argParams[i]);
						}
						if (fits) {
							met.setAccessible(true);
							met.invoke(inst, args);
							invoked = true;
							System.out.println(inst.toString());
						}
					}
				}

				clazz = clazz.getSuperclass();
			}
			if (!invoked) {
				throw new IllegalArgumentException();
			}

		} catch (ClassNotFoundException e) {
			System.out.println("Class " + className + " not found");
		} catch (IllegalAccessException e) {
			System.out.println(e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println("Method " + methodName + " with argumetns "
					+ args.toString() + " not found");
		} catch (InstantiationException e) {
			if (clazz.isInterface()) {
				System.out.println("Class " + className + " is interface");
			} else if (clazz.isArray()) {
				System.out.println("Class " + className + " is array");
			} else {
				System.out.println("Class " + className + " is primitive");
			}
		} catch (InvocationTargetException e) {
			System.out.println(e.getTargetException().getMessage());
		}
	}

	public static void main(String[] args) throws NoSuchMethodException {
		if (args.length < 2) {
			System.out.println("Not enough arguments");
			return;
		}

		Object[] arguments = new Object[args.length - 2];
		for (int i = 2; i < args.length; i++) {
			arguments[i - 2] = args[i];
		}

		execute(args[0], args[1], arguments);
	}

}
