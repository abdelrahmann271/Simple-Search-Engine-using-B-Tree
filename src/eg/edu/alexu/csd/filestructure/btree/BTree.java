package eg.edu.alexu.csd.filestructure.btree;
import java.util.ArrayList;
import java.util.List;


public class BTree<K extends Comparable<K>, V> implements IBTree<K, V> {
	
	private int degree;
	private IBTreeNode<K, V> root = null;
	private IBTreeNode<K, V> currentParent = null;
	private IBTreeNode<K, V> NodeToInsertIn = null;
	
	public BTree(int degree) {
		// TODO Auto-generated constructor stub
		this.degree = degree;
		root = new BTreeNode<K,V>(degree);
	}
	
	@Override
	public int getMinimumDegree() {
		// TODO Auto-generated method stub
		return this.degree;
	}

	@Override
	public IBTreeNode<K, V> getRoot() {
		// TODO Auto-generated method stub
		return this.root;
	}

	@Override
	public void insert(K key, V value) {
		// TODO Auto-generated method stub
//		IBTreeNode<K, V> nodeToinsertIn = searchNode(root, key);
//		if( nodeToinsertIn == null)
//		{
//			root.getChildren().add(0, null);
//			root.getChildren().add(1, null);
//			root.getKeys().add(0, key);
//			root.getValues().add(0, value);
//			root.setLeaf(true);
//		}
//		else {
//			
//		}
		searchForInsert(this.root, key);
		NodeToInsertIn.getKeys().add(key);
		NodeToInsertIn.getValues().add(value);
		for(int i = NodeToInsertIn.getKeys().size()-1 ; i > 0 ; i--) {
			
			if( NodeToInsertIn.getKeys().get(i).compareTo(NodeToInsertIn.getKeys().get(i-1)) < 0 ) {
				
				swapKeys(  NodeToInsertIn.getKeys().get(i) , NodeToInsertIn.getKeys().get(i-1)  );
				swapValues(   NodeToInsertIn.getValues().get(i) , NodeToInsertIn.getValues().get(i-1) );
			}
			else {
				break;
			}
			
		}
		int size = NodeToInsertIn.getKeys().size();
		if( size  > degree) {
			int median = ( size-1 ) / 2 ;
			
			List<K> leftKeys = new ArrayList<K>();
			List<V> leftValues = new ArrayList<V>();
			List<K> rightKeys = new ArrayList<K>();
			List<V> rightValues = new ArrayList<V>();
			
			for(int k = 0 ; k < median ; k++) {
				leftKeys.add(NodeToInsertIn.getKeys().get(k));
				leftValues.add(NodeToInsertIn.getValues().get(k));
			}
			for(int k = median + 1 ; k < size ; k++) {
				rightKeys.add(NodeToInsertIn.getKeys().get(k));
				rightValues.add(NodeToInsertIn.getValues().get(k));
			}
			
			IBTreeNode<K, V> leftNode = new BTreeNode<>(degree);
			IBTreeNode<K, V> rightNode = new BTreeNode<>(degree);
			leftNode.setKeys(leftKeys);
			leftNode.setValues(leftValues);
			rightNode.setKeys(rightKeys);
			rightNode.setValues(rightValues);
			
		}
	}
	
	private void searchForInsert(IBTreeNode<K, V> node , K key )  {
		if(node.getKeys().size() == 0) {	//Insert at root.
			
		}
		for(int i = 0 ; i < node.getKeys().size() ; i++) {
			//Continue searching into left.
			if(node.isLeaf() == false  && node.getKeys().get(i).compareTo(key) > 0) {
				
				currentParent = node;
				searchForInsert(node.getChildren().get(i), key);
				
			}//Continue searching into right.
			else if ( node.isLeaf() == false && i == node.getKeys().size()-1 && node.getKeys().get(i).compareTo(key) < 0 ) {
				
				currentParent = node;
				searchForInsert(node.getChildren().get(i+1), key);
				
			}//Key will be inserted in this node, Insert instead of this key and fix-up if necessary.
			else if (node.isLeaf() == true && node.getKeys().get(i).compareTo(key) > 0 ) {
				
				NodeToInsertIn = node;
				return;
				
			}//Insert at the end cause all the elements are smaller than it and it's the end.
			else if (node.isLeaf() == true && i == node.getKeys().size()-1 && node.getKeys().get(i).compareTo(key) > 0 ) {
				
				NodeToInsertIn = node;
				return;
				
			}
		}
	}
	
//	private IBTreeNode<K, V> searchNode(IBTreeNode<K, V> node,K key) {
//		
//		if(node.getKeys().size() == 0) //Insert in the root or key is not found in search.
//			return null;
//		for(int i = 0 ; i < node.getKeys().size() ; i++) {
//			
//			//The key is not in this node and it is not a leaf node , So we will continue searching.
//			if( i+1 == node.getKeys().size() && !node.isLeaf() ) {
//				
//				return this.searchNode(node.getChildren().get(i+1) , key );
//			}
//			
//			//The key is not in this node and it is a leaf node ,So we will return the node to insert in it or search in it
//			if( i+1 == node.getKeys().size() && node.isLeaf() ) {
//				return node;
//			}
//			
//			//The key is found or it's not found
//			if( node.getKeys().get(i).compareTo(key) >= 0 )
//			{
//				return node;
//			}
//			
//			
//			
//		}
//		return null;
//	}

	@Override
	public V search(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(K key) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void swapValues (V x , V y) {
		V temp = x;
		x = y;
		y = temp;
	}
	
	private void swapKeys (K x , K y) {
		K temp = x;
		x = y;
		y = temp;
	}

}
