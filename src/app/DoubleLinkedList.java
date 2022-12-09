/*
สร้าง Doubly Linked List
 */
package app;
import java.util.ArrayList;
public class DoubleLinkedList implements ListADT{
    Object e;
    DoubleMyNode first,current;
    ArrayList<MusicInfo> songList = new ArrayList<MusicInfo>();
    int size;
    public DoubleLinkedList()
    {
        List();
    }
    @Override
    public void List() {
        first=current= null;
    }

    @Override
    //insert node
    //เพิ่ม node ลงใน Doubly Linked List (รับ parameter Object แล้วนำไปสร้าง node)
    public void insert(Object e) throws Exception {
        DoubleMyNode p = new DoubleMyNode(e,null,null);
        if (isEmpty()){
            first=p;
        }else{
            p.setNextNode(current.getNextNode());
            if(current.getNextNode()!=null) {
                current.getNextNode().setPreviousNode(p);
            }
            current.setNextNode(p);
            p.setPreviousNode(current);
        }
        current=p;
        songList.add((MusicInfo) e);
    }

    @Override
    //retrieve object
    //เรียกค่าของข้อมูลที่เก็บ node
    public Object retrieve() throws Exception {
        if (isEmpty()) {
            throw new Exception("List is empty");
        }
        else {
            return current.getData();
        }
    }

    @Override
    //delete node
    //ลบ node
    public void delete() throws Exception {
        if (isEmpty()) {
            throw new Exception("List is empty");
        }else{
            first=current= null;
            songList = new ArrayList<MusicInfo>();
        }
    }

    @Override
    //update data
    //update ข้อมูลใน node
    public void update(Object e) throws Exception {
        current.setData(e);
    }

    @Override
    //move to first position
    //ย้ายไปยัง node แรก
    public void findFirst() throws Exception {
        current = first;
    }

    @Override
    //move to next node
    //ย้ายไปยัง node ถัดไป
    public void findNext() throws Exception {
        if(current.getNextNode()!=null) {
            current = current.getNextNode();
        }else throw new Exception("This is last song of playlist!");
    }

    @Override
    //move to previous node
    //ย้ายไปยัง node ก่อนหน้า
    public void findPrev() throws Exception {
        if (current.getPreviousNode() != null) {
            current = current.getPreviousNode();
        } else throw new Exception("This is first song of playlist!");
    }

    @Override
    //ตรวจสอบว่าว่างเปล่าหรือไม่
    public boolean isEmpty() {
        return first == null && current == null;
    }

    @Override
    //ตรวจสอบว่าเต็มหรือไม่
    public boolean isFull() {
        return false;
    }

    //เรียกดูรายชื่อเพลงทั้งหมดในลำดับการเล่น
    public ArrayList<MusicInfo> getSongList(){
        return songList;
    }
}
