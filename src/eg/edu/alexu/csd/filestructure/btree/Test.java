package eg.edu.alexu.csd.filestructure.btree;


import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class Test  {
	Vector <IBTreeNode> V=new Vector<IBTreeNode>();
	
	public void print(IBTreeNode<Integer, String> node ) {
	     V.add(node);
	        System.out.println(node.getKeys());
	        while(!V.isEmpty()) {
	            IBTreeNode temp=V.firstElement();
	            V.remove(0);
	            int n=temp.getChildren().size();
	            for(int i=0;i<n;i++) {
	                V.add((IBTreeNode)temp.getChildren().get(i));
	                System.out.print(((IBTreeNode)temp.getChildren().get(i)).getKeys());
	            }
	            if(n!=0) {
	            System.out.println();}
	        }
	}
	
	public static void main(String[] args) {
		

		IBTree<Integer, String> tree = new BTree<Integer, String>(3);
//		List<Integer> inp = Arrays.asList(new Integer[]{1, 3, 7, 10, 11, 13, 14, 15, 18, 16, 19, 24, 25, 26, 21, 4, 5, 20, 22, 2, 17, 12, 6});
//		for (int i : inp)
//			tree.insert(i, "Soso" + i);
//		
		Test f = new Test();
//		
//		f.print(tree.getRoot());
//		
//		System.out.println(tree.delete(6) + "6");
//		f.print(tree.getRoot());
//		System.out.println(tree.delete(13) +"13");
//		f.print(tree.getRoot());
//		System.out.println(tree.delete(7) + "7");
//		f.print(tree.getRoot());
//		System.out.println(tree.delete(4) + "4");
//		f.print(tree.getRoot());
//		System.out.println(tree.delete(2) + "2");
//		f.print(tree.getRoot());
//		System.out.println(tree.delete(16) + "16");
		
		List<Integer> inp = Arrays.asList(new Integer[]{1, 3, 7, 10, 11, 13, 14, 15, 18, 16, 19, 24, 25, 26, 21, 4, 5, 20, 22, 2, 17, 12, 6});
		for (int i : inp)
			tree.insert(i, "Soso" + i);
		
		
		f.print(tree.getRoot());
		
		System.out.println(tree.delete(25) + "25 ");
		f.print(tree.getRoot());
		System.out.println(tree.delete(13) + "13 ");
		f.print(tree.getRoot());
		System.out.println(tree.delete(12) + "12 ");
		f.print(tree.getRoot());
		System.out.println(tree.delete(15) + "15 ");
		f.print(tree.getRoot());
		System.out.println(tree.delete(19) + "19 ");
		f.print(tree.getRoot());
		System.out.println(tree.delete(26) + "26 ");
		f.print(tree.getRoot());
		System.out.println(tree.delete(17) + "17 ");
		f.print(tree.getRoot());
		System.out.println(tree.delete(21) + "21 ");
		f.print(tree.getRoot());
		System.out.println(tree.delete(14) + "14 ");
		f.print(tree.getRoot());
		System.out.println(tree.delete(18) + "18 ");
		f.print(tree.getRoot());
		System.out.println(tree.delete(4) + "4 ");
		f.print(tree.getRoot());
		System.out.println(tree.delete(1) + "1 ");
		f.print(tree.getRoot());
		System.out.println(tree.delete(3) + "3 ");
		f.print(tree.getRoot());
		System.out.println(tree.delete(20) + "20 ");
		f.print(tree.getRoot());
		System.out.println(tree.delete(7) + "7");
		f.print(tree.getRoot());
		System.out.println(tree.delete(16) + "16 ");
		f.print(tree.getRoot());
		System.out.println(tree.delete(5) + "5 ");
		f.print(tree.getRoot());
		System.out.println(tree.delete(10) + "10 ");
		f.print(tree.getRoot());
		System.out.println(tree.delete(11) + "11 ");
		f.print(tree.getRoot());
		System.out.println(tree.delete(6) + "6 ");
		f.print(tree.getRoot());

//		25
//		13
//		12
//		15
//		19
//		26
//		17
//		21
//		14
//		18
//		4
//		1
//		3
//		20
//		7
//		16
//		5
//		10
//		11
//		6
		
		

		
		
		

	        

		
		
		

		

		
	}
}