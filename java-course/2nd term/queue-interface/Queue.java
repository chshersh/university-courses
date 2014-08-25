//inv: size() >= 0
public interface Queue extends Copiable {
	//pre: element != null
	void push(Object element);
	//post: size() = size()' + 1
	
	//pre: size() > 0
	Object pop();
	//post: size() = size()' - 1
	
	//pre: size() > 0
	Object peek();
	//post: 
	
	//pre:
	int size();
	//post: res = size
	
	//pre:
	boolean isEmpty();
	//post: res = (size() == 0)
	
	//pre:
	Queue makeCopy();
	//post: result = copyOf(this)
}