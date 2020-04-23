package eg.edu.alexu.csd.filestructure.btree;


import java.util.List;

public class BTreeNode<K extends Comparable<K>, V> implements IBTreeNode<K, V> {

	private int numOfKeys;
	private int numOfChilds;
	private Boolean isLeaf;
	private List<K> keys = null;
	private List<V> values = null;
	private List<IBTreeNode<K, V>> children = null;

	
	public BTreeNode(int numOfChilds) {
		// TODO Auto-generated constructor stub
		this.numOfChilds = numOfChilds;
		this.numOfKeys = numOfChilds-1;
		this.isLeaf = true;
	}
	@Override
	public int getNumOfKeys() {
		// TODO Auto-generated method stub
		return this.numOfKeys;
	}

	@Override
	public void setNumOfKeys(int numOfKeys) {
		// TODO Auto-generated method stub
		this.numOfKeys = numOfKeys;
	}

	@Override
	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return isLeaf;
	}

	@Override
	public void setLeaf(boolean isLeaf) {
		// TODO Auto-generated method stub
		this.isLeaf = isLeaf;
	}

	@Override
	public List<K> getKeys() {
		// TODO Auto-generated method stub
		return this.keys;
	}

	@Override
	public void setKeys(List<K> keys) {
		// TODO Auto-generated method stub
		this.keys = keys;
	}

	@Override
	public List<V> getValues() {
		// TODO Auto-generated method stub
		return this.values;
	}

	@Override
	public void setValues(List<V> values) {
		// TODO Auto-generated method stub
		this.values = values;
	}

	@Override
	public List<IBTreeNode<K, V>> getChildren() {
		// TODO Auto-generated method stub
		return this.children;
	}

	@Override
	public void setChildren(List<IBTreeNode<K, V>> children) {
		// TODO Auto-generated method stub
		this.children = children;
	}

	

}
