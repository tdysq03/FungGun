/*
สร้าง node สำหรับ Doubly Linked List
 */
package app;
public class DoubleMyNode {
	 private Object data;
	 private DoubleMyNode nextNode,prevNode;
	 DoubleMyNode(Object object){
	        this( object, null,null);
	 }
	 DoubleMyNode(Object object, DoubleMyNode node1, DoubleMyNode node2){
		 data = object;
		 prevNode = node1;
		 nextNode = node2;
	 }

	 //set next node
	 public void setNextNode( DoubleMyNode node){
		 nextNode = node ;
	 }

	 //set previous node
	 public void setPreviousNode( DoubleMyNode node){
		 prevNode = node ;
	 }

	 //get next node
	 public DoubleMyNode getNextNode(){
		 return nextNode;
	 }

	 //get previous node
	 public DoubleMyNode getPreviousNode(){
		 return prevNode ;
	 }

	 //set new data
	 public void setData(Object info){
		 data = info;
	 }

	 //get data (object)
	 public Object getData(){
		 return data;
	 }
	    
}
