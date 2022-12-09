//ListADT
package app;
public interface ListADT {
    public void List();
    public void insert(Object e) throws Exception;
    public Object retrieve() throws Exception;
    public void delete() throws Exception;
    public void update(Object e) throws Exception;
    public void findFirst() throws Exception;
    public void findNext() throws Exception;
    public void findPrev() throws Exception;
    public boolean isEmpty();
    public boolean isFull();
}
