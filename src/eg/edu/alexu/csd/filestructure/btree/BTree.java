package eg.edu.alexu.csd.filestructure.btree;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Vector;


public class BTree<K extends Comparable<K>, V> implements IBTree<K, V> {
	
	private int degree;
	private int maxKeys;
	private int minKeys;
	private IBTreeNode<K, V> root = null;
	private IBTreeNode<K, V> NodeToInsertIn = null;
	private IBTreeNode<K, V> NodeToSearchIn = null;
	private Vector<IBTreeNode<K, V>> parents = new Vector<>();
	private Boolean keyFound = false; //This value is used when we insert an existed key, so if we found it we set it to true in order to not to insert it in the insert function
									 //it's important to reset this value.
	private Vector<Integer> NodeAsAchildIndx = new Vector<Integer>();
	
	public BTree(int degree) {
		// TODO Auto-generated constructor stub
		this.degree = degree;
		root = new BTreeNode<K,V>(degree);
		this.maxKeys = degree-1;
		this.minKeys = (int) (Math.ceil((double)degree/2)-1);
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
		keyFound = false;
		searchForInsert(this.root, key);
		if(keyFound == true) {
			keyFound = false;
			return;
		}
		NodeToInsertIn.getKeys().add(key);
		NodeToInsertIn.getValues().add(value);
		
		//Inserting at Leaf.
		for(int i = NodeToInsertIn.getKeys().size()-1 ; i > 0 ; i--) {
			
			if( NodeToInsertIn.getKeys().get(i).compareTo(NodeToInsertIn.getKeys().get(i-1)) < 0 ) {
				
				K temp =  NodeToInsertIn.getKeys().get(i);
				NodeToInsertIn.getKeys().set(i,  NodeToInsertIn.getKeys().get(i-1));
				NodeToInsertIn.getKeys().set(i-1, temp);
				
				V temp2 =  NodeToInsertIn.getValues().get(i);
				NodeToInsertIn.getValues().set(i,  NodeToInsertIn.getValues().get(i-1));
				NodeToInsertIn.getValues().set(i-1, temp2);
				
			}
			else {
				break;
			}
			
		}
		int size = NodeToInsertIn.getKeys().size();
		if( size  > maxKeys) {
			int median = ( size-1 ) / 2 ;
			
			K medianKey = NodeToInsertIn.getKeys().get(median);
			V medianValue = NodeToInsertIn.getValues().get(median);
			
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
			
			leftNode.setLeaf(true);
			rightNode.setLeaf(true);
			
			leftNode.setKeys(leftKeys);
			leftNode.setValues(leftValues);
			rightNode.setKeys(rightKeys);
			rightNode.setValues(rightValues);
			
			insert_fixup( medianKey, medianValue, leftNode, rightNode	);
			
		}
		
		parents.clear();
		
	}
	
	
	private void insert_fixup(K key , V value , IBTreeNode<K, V> left , IBTreeNode<K, V> right ) {
		
		IBTreeNode<K, V> currentParent = null;
		Boolean flag = true;
		//check if parent is null , its root.
//		System.out.println(parents.size());
		if(parents.size() == 0) {
			currentParent = new BTreeNode<K,V>(this.degree);
			this.root = currentParent;
			currentParent.setLeaf(false);
			flag = false;
		} 
		else {
			currentParent = parents.get(parents.size()-1);
			parents.remove(parents.size()-1);
		}
		

		
		currentParent.getKeys().add(key);
		currentParent.getValues().add(value);
		int i;
		for( i = currentParent.getKeys().size()-1 ; i > 0 ; i--) {
			
			if( currentParent.getKeys().get(i).compareTo(currentParent.getKeys().get(i-1)) < 0 ) {
				
				K temp =  currentParent.getKeys().get(i);
				currentParent.getKeys().set(i,  currentParent.getKeys().get(i-1));
				currentParent.getKeys().set(i-1, temp);
				
				V temp2 =  currentParent.getValues().get(i);
				currentParent.getValues().set(i,  currentParent.getValues().get(i-1));
				currentParent.getValues().set(i-1, temp2);
			}
			else {
				break;
			}
			
		}
		//update children.
		//because we inserted in a not leaf node.
		//change i child and add the right child at the end and put it to i+1 .
		if(flag) {
			currentParent.getChildren().set(i, left);
			currentParent.getChildren().add(right);
			for(int j = currentParent.getChildren().size()-1 ; j>=i+2 ; j--) {
				IBTreeNode<K, V> temp = currentParent.getChildren().get(j-1);
				currentParent.getChildren().set(j-1, currentParent.getChildren().get(j));
				currentParent.getChildren().set(j, temp);
			}
		}
		else {
			currentParent.getChildren().add(left);
			currentParent.getChildren().add(right);
		}

		//check if it overloaded
		int size = currentParent.getKeys().size();
		//Don't forget to insert children at splitting!.
		if( size  > maxKeys) {
			int median = ( size-1 ) / 2 ;
			
			K medianKey = currentParent.getKeys().get(median);
			V medianValue = currentParent.getValues().get(median);
			
			List<K> leftKeys = new ArrayList<K>();
			List<V> leftValues = new ArrayList<V>();
			List<K> rightKeys = new ArrayList<K>();
			List<V> rightValues = new ArrayList<V>();
			List<IBTreeNode<K, V>> leftChildren = new ArrayList<IBTreeNode<K,V>>();
			List<IBTreeNode<K, V>> rightChildren = new ArrayList<IBTreeNode<K,V>>();
			
			for(int k = 0 ; k < median ; k++) {
				leftKeys.add(currentParent.getKeys().get(k));
				leftValues.add(currentParent.getValues().get(k));
			}
			for(int k = median + 1 ; k < size ; k++) {
				rightKeys.add(currentParent.getKeys().get(k));
				rightValues.add(currentParent.getValues().get(k));
			}
			
			for(int k = 0 ; k <= median ; k++) {
				leftChildren.add(currentParent.getChildren().get(k) );
			}
			for(int k = median+1 ; k < currentParent.getChildren().size() ; k++) {
				rightChildren.add(currentParent.getChildren().get(k) );
			}
			
			IBTreeNode<K, V> leftNode = new BTreeNode<>(degree);
			IBTreeNode<K, V> rightNode = new BTreeNode<>(degree);
			
			leftNode.setLeaf(false);
			rightNode.setLeaf(false);
			
			leftNode.setKeys(leftKeys);
			leftNode.setValues(leftValues);
			leftNode.setChildren(leftChildren);
			
			rightNode.setKeys(rightKeys);
			rightNode.setValues(rightValues);
			rightNode.setChildren(rightChildren);
			
			insert_fixup(medianKey, medianValue, leftNode, rightNode);
			
		}
		
		return;
		
	}
	
	private void searchForInsert(IBTreeNode<K, V> node , K key )  {
		if(node.getKeys().size() == 0) {	//Insert at root.
			NodeToInsertIn = this.root;		
		}
		for(int i = 0 ; i < node.getKeys().size() ; i++) {
			//Continue searching into left.
			if(node.getKeys().get(i).compareTo(key) == 0) {
				NodeToSearchIn = node;
				keyFound = true;
				return;
			}
			if(node.isLeaf() == false  && node.getKeys().get(i).compareTo(key) > 0) {
				
				//currentParent = node;
				parents.add(node);
				NodeAsAchildIndx.add(i);
				searchForInsert(node.getChildren().get(i), key);
				return;
				
			}//Continue searching into right.
			else if ( node.isLeaf() == false && i == node.getKeys().size()-1 && node.getKeys().get(i).compareTo(key) < 0 ) {
				
				//currentParent = node;
				parents.add(node);
				NodeAsAchildIndx.add(i+1);
				searchForInsert(node.getChildren().get(i+1), key);
				return;
				
			}//Key will be inserted in this node, Insert instead of this key and fix-up if necessary.
			else if (node.isLeaf() == true && node.getKeys().get(i).compareTo(key) > 0 ) {
				
				NodeToInsertIn = node;
				return;
				
			}//Insert at the end cause all the elements are smaller than it and it's the end.
			else if (node.isLeaf() == true && i == node.getKeys().size()-1 && node.getKeys().get(i).compareTo(key) < 0 ) {
				
				NodeToInsertIn = node;
				return;
				
			}
		}
	}
	
	@Override
	public V search(K key) {
		// TODO Auto-generated method stub
		NodeToSearchIn = null;
		searchForInsert(this.root, key);
		if(NodeToSearchIn == null) {
			return null;
		}
		for(int i = 0 ; i < NodeToSearchIn.getKeys().size() ; i++) {
			if(NodeToSearchIn.getKeys().get(i).compareTo(key) == 0) {
				parents.clear();
				return NodeToSearchIn.getValues().get(i);
			}
		}
		parents.clear();
		return null;
	}
	
	
	private void fix_delete() {
		
	}

	@Override
	public boolean delete(K key) {
		// TODO Auto-generated method stub
		keyFound = false;
		searchForInsert(this.root, key);
		if(keyFound == true) {
			keyFound = false;
			return false;
		}
		
		//Case 1 : Contains more than min number of keys and it's a leaf node.
		//Delete it.
		if(NodeToSearchIn.isLeaf() == true) {
			
			IBTreeNode<K, V> currentParent = parents.get( parents.size()-1 );
			int indx = NodeAsAchildIndx.get( NodeAsAchildIndx.size()-1 );
			
			if(NodeToSearchIn.getKeys().size() > minKeys) {
				int index = 0;
				//Get the index.
				for(int i = 0 ; i < NodeToSearchIn.getKeys().size() ; i++) {
					if(NodeToSearchIn.getKeys().get(i).compareTo(key) == 0) {
						index = i;
						break;
					}
				}
				NodeToSearchIn.getKeys().remove(index);
				NodeToSearchIn.getValues().remove(index);
			}
			//Case 2 : Contains min number of keys and it's a leaf node.
			else {
				

				//Try to borrow from left.
				if( indx-1 >= 0 && currentParent.getChildren().get(indx-1).getKeys().size() > minKeys ) {
					
					int indexLast = currentParent.getChildren().get(indx-1).getKeys().size()-1;
					
					K lastKey =  currentParent.getChildren().get(indx-1).getKeys().get(indexLast);
					V lastValue =  currentParent.getChildren().get(indx-1).getValues().get(indexLast);
					
					currentParent.getChildren().get(indx-1).getKeys().remove(indexLast);
					currentParent.getChildren().get(indx-1).getValues().remove(indexLast);
					
					K parentKey = currentParent.getKeys().get(indx-1);
					V parentValue = currentParent.getValues().get(indx-1);
					
					currentParent.getKeys().set(indx-1, lastKey);
					currentParent.getValues().set(indx-1, lastValue);
					
					//remove the key
					for(int i = 0 ; i < NodeToSearchIn.getKeys().size() ; i++) {
						if(NodeToSearchIn.getKeys().get(i).compareTo(key) == 0) {
							NodeToSearchIn.getKeys().remove(i);
							NodeToSearchIn.getValues().remove(i);
						}
					}
					
					//insert parent.
					NodeToSearchIn.getKeys().add(parentKey);
					NodeToSearchIn.getValues().add(parentValue);
					
					//adjust its place.
					for(int i = NodeToSearchIn.getKeys().size()-1 ; i > 0 ; i--) {
						if( NodeToSearchIn.getKeys().get(i).compareTo(NodeToSearchIn.getKeys().get(i-1)) < 0 ) {
							K temp =  NodeToSearchIn.getKeys().get(i);
							NodeToSearchIn.getKeys().set(i,  NodeToSearchIn.getKeys().get(i-1));
							NodeToSearchIn.getKeys().set(i-1, temp);
							V temp2 =  NodeToSearchIn.getValues().get(i);
							NodeToSearchIn.getValues().set(i, NodeToSearchIn.getValues().get(i-1));
							NodeToSearchIn.getValues().set(i-1, temp2);
						}
						else {
							break;
						}
						
					}
				}
				//Try to borrow from right.
				else if( indx+1 <  currentParent.getChildren().size() && currentParent.getChildren().get(indx+1).getKeys().size() > minKeys ) {
					

					//Try to borrow from left.
					if( indx+1 != 0 && currentParent.getChildren().get(indx+1).getKeys().size() > minKeys ) {
						
						
						K FirstKey =  currentParent.getChildren().get(indx-1).getKeys().get(0);
						V FirstValue =  currentParent.getChildren().get(indx-1).getValues().get(0);
						
						currentParent.getChildren().get(indx-1).getKeys().remove(0);
						currentParent.getChildren().get(indx-1).getValues().remove(0);
						
						K parentKey = currentParent.getKeys().get(indx);
						V parentValue = currentParent.getValues().get(indx);
						
						currentParent.getKeys().set(indx, FirstKey);
						currentParent.getValues().set(indx, FirstValue);
						
						//remove the key
						for(int i = 0 ; i < NodeToSearchIn.getKeys().size() ; i++) {
							if(NodeToSearchIn.getKeys().get(i).compareTo(key) == 0) {
								NodeToSearchIn.getKeys().remove(i);
								NodeToSearchIn.getValues().remove(i);
							}
						}
						
						//insert parent.
						NodeToSearchIn.getKeys().add(parentKey);
						NodeToSearchIn.getValues().add(parentValue);
						
						//adjust its place.
						for(int i = NodeToSearchIn.getKeys().size()-1 ; i > 0 ; i--) {
							if( NodeToSearchIn.getKeys().get(i).compareTo(NodeToSearchIn.getKeys().get(i-1)) < 0 ) {
								K temp =  NodeToSearchIn.getKeys().get(i);
								NodeToSearchIn.getKeys().set(i,  NodeToSearchIn.getKeys().get(i-1));
								NodeToSearchIn.getKeys().set(i-1, temp);
								V temp2 =  NodeToSearchIn.getValues().get(i);
								NodeToSearchIn.getValues().set(i, NodeToSearchIn.getValues().get(i-1));
								NodeToSearchIn.getValues().set(i-1, temp2);
							}
							else {
								break;
							}
						}
				}
					
				}
				//Merge. The default is merging with left if it exists. else ..> Merge with right.
				else {
					//Merge with left.
					if(indx-1 >= 0) {
						IBTreeNode<K, V> mergedNode = new BTreeNode<K, V>(this.degree);
						List<K> mergedKeys = new ArrayList<K>();
						List<V> mergedValues = new ArrayList<V>();;
						
						//Merge two nodes with the exception of the desired one to be deleted and add the parent
						for(int i = 0 ; i < currentParent.getChildren().get(indx-1).getKeys().size();i++) {
							
						
								mergedKeys.add(currentParent.getChildren().get(indx-1).getKeys().get(i));
								mergedValues.add(currentParent.getChildren().get(indx-1).getValues().get(i));


						}
						//add what is at the parent then delete it.
						mergedKeys.add(currentParent.getKeys().get(indx-1));
						mergedValues.add(currentParent.getValues().get(indx-1));
						
						currentParent.getKeys().remove(indx-1);
						currentParent.getValues().remove(indx-1);
						currentParent.getChildren().remove(indx-1);
						
						
						for(int i = 0 ; i < currentParent.getChildren().get(indx).getKeys().size();i++) {
							
							if(currentParent.getChildren().get(indx-1).getKeys().get(i).compareTo(key) != 0) {
							mergedKeys.add(currentParent.getChildren().get(indx).getKeys().get(i));
							mergedValues.add(currentParent.getChildren().get(indx).getValues().get(i));
							}
						}
						
						mergedNode.setKeys(mergedKeys);
						mergedNode.setValues(mergedValues);
						mergedNode.setLeaf(true);
						
						currentParent.getChildren().set(indx-1, mergedNode );
						
					}
					//Merge with right.
					else {
						
					}
				}
			}			
		}
		//Case it'not not leaf node.	
		return true;
		
	}
}
