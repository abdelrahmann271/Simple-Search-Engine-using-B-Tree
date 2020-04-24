package eg.edu.alexu.csd.filestructure.btree;

import java.awt.List;
import java.util.ArrayList;

public class Test {
	public static void main(String[] args) {
		
//		java.util.List<Integer> s = new ArrayList<Integer>();
//		s.add(2);
//		System.out.println(s.size());
		
//		//Test 1 Insert - Passed
//		BTree<Integer, Integer> tree = new BTree<>(3);
//		tree.insert(1, 1);
//		tree.insert(2, 2);
//		tree.insert(3, 3);
//		tree.insert(4, 4);
//		tree.insert(5, 5);
//		tree.insert(6, 6);
//		tree.insert(7, 7);
//		tree.insert(8, 8);
//		tree.insert(9, 9);
//		tree.insert(10, 10);
//
//		
//		//root
//		for(int i = 0 ; i < tree.getRoot().getKeys().size() ; i++ ) {
//			System.out.print(tree.getRoot().getKeys().get(i)+" ");
//		}
//		System.out.println();
//		System.out.println();
//		
//		//left root
//		for(int i = 0 ; i < tree.getRoot().getChildren().get(0).getKeys().size() ; i++ ) {
//			System.out.print(tree.getRoot().getChildren().get(0).getKeys().get(i)+" ");
//		}
//		System.out.println();
//		System.out.println();
//		
//		//right root
//		for(int i = 0 ; i < tree.getRoot().getChildren().get(1).getKeys().size() ; i++ ) {
//			System.out.print(tree.getRoot().getChildren().get(1).getKeys().get(i)+" ");
//		}
//		System.out.println();
//		System.out.println();
//		
//
//		//left left root
//		for(int i = 0 ; i < tree.getRoot().getChildren().get(0).getChildren().get(0).getKeys().size() ; i++ ) {
//			System.out.print(tree.getRoot().getChildren().get(0).getChildren().get(0).getKeys().get(i)+" ");
//		}
//		System.out.println();
//		System.out.println();
//		
//		//left right root
//		for(int i = 0 ; i < tree.getRoot().getChildren().get(0).getChildren().get(1).getKeys().size() ; i++ ) {
//			System.out.print(tree.getRoot().getChildren().get(0).getChildren().get(1).getKeys().get(i)+" ");
//		}
//		System.out.println();
//		System.out.println();
//		
//		//right left root
//		
//		for(int i = 0 ; i < tree.getRoot().getChildren().get(1).getChildren().get(0).getKeys().size() ; i++ ) {
//			System.out.print(tree.getRoot().getChildren().get(1).getChildren().get(0).getKeys().get(i)+" ");
//		}
//		System.out.println();
//		System.out.println();
//		
//		
//		//right right root
//		
//		for(int i = 0 ; i < tree.getRoot().getChildren().get(1).getChildren().get(1).getKeys().size() ; i++ ) {
//			System.out.print(tree.getRoot().getChildren().get(1).getChildren().get(1).getKeys().get(i)+" ");
//		}
//		System.out.println();
//		System.out.println();
//		
//		for(int i = 0 ; i < tree.getRoot().getChildren().get(1).getChildren().get(2).getKeys().size() ; i++ ) {
//			System.out.print(tree.getRoot().getChildren().get(1).getChildren().get(2).getKeys().get(i)+" ");
//		}
//		System.out.println();
//		System.out.println();
		
//		//Test 2 : Insert
//		//5,3,21,9,1,13,2,7,10,12,4,8
//		
//		BTree<Integer, Integer> tree = new BTree<Integer, Integer>(4);
//		
//		tree.insert(5, 5);
//		tree.insert(3, 3);
//		tree.insert(21, 21);
//		tree.insert(9, 9);
//		tree.insert(1, 1);
//		tree.insert(13, 13);
//		tree.insert(2, 2);
//		tree.insert(7, 7);
//		tree.insert(10, 10);
//		tree.insert(12, 12);
//		tree.insert(4, 4);
//		tree.insert(8, 8);
//		
//		//root
//		for(int i = 0 ; i < tree.getRoot().getKeys().size() ; i++ ) {
//			System.out.print(tree.getRoot().getKeys().get(i)+" ");
//		}
//		System.out.println();
//		System.out.println();
//		
//		for(int i = 0 ; i < tree.getRoot().getChildren().get(0).getKeys().size() ; i++ ) {
//			System.out.print(tree.getRoot().getChildren().get(0).getKeys().get(i)+" ");
//		}
//		System.out.println();
//		System.out.println();
//		
//		for(int i = 0 ; i < tree.getRoot().getChildren().get(1).getKeys().size() ; i++ ) {
//			System.out.print(tree.getRoot().getChildren().get(1).getKeys().get(i)+" ");
//		}
//		System.out.println();
//		System.out.println();
//		
//		for(int i = 0 ; i < tree.getRoot().getChildren().get(0).getChildren().get(0).getKeys().size() ; i++ ) {
//			System.out.print(tree.getRoot().getChildren().get(0).getChildren().get(0).getKeys().get(i)+" ");
//		}
//		System.out.println();
//		System.out.println();
//		
//		for(int i = 0 ; i < tree.getRoot().getChildren().get(0).getChildren().get(1).getKeys().size() ; i++ ) {
//			System.out.print(tree.getRoot().getChildren().get(0).getChildren().get(1).getKeys().get(i)+" ");
//		}
//		System.out.println();
//		System.out.println();
//		
//		for(int i = 0 ; i < tree.getRoot().getChildren().get(1).getChildren().get(0).getKeys().size() ; i++ ) {
//			System.out.print(tree.getRoot().getChildren().get(1).getChildren().get(0).getKeys().get(i)+" ");
//		}
//		System.out.println();
//		System.out.println();
//		
//		for(int i = 0 ; i < tree.getRoot().getChildren().get(1).getChildren().get(1).getKeys().size() ; i++ ) {
//			System.out.print(tree.getRoot().getChildren().get(1).getChildren().get(1).getKeys().get(i)+" ");
//		}
//		System.out.println();
//		System.out.println();
//		
//		for(int i = 0 ; i < tree.getRoot().getChildren().get(1).getChildren().get(2).getKeys().size() ; i++ ) {
//			System.out.print(tree.getRoot().getChildren().get(1).getChildren().get(2).getKeys().get(i)+" ");
//		}
//		System.out.println();
//		System.out.println();
		

		
	}
}
