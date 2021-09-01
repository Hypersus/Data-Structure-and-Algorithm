package top.zekehypersus.binarytree;


public class MyTree<K,V> {
	class TreeNode{
		K key;
		V value;
		TreeNode parent;
		TreeNode left;
		TreeNode right;
		public TreeNode() {
			
		}
		public TreeNode(K key,V value,TreeNode parent,TreeNode left,TreeNode right) {
			// TODO Auto-generated constructor stub
			this.key=key;
			this.value=value;
			this.parent=parent;
			this.left=left;
			this.right=right;
		}
		public TreeNode(K key,V value) {
			set(key, value);
		}
		public void set(K key, V value) {
			this.key=key;
			this.value=value;
		}
		public void setKey(K key) {
			this.key=key;
		}
		public void setValue(V value) {
			this.value=value;
		}
		public void setPar(TreeNode parent) {
			this.parent=parent;
		}
		public void setLeft(TreeNode left) {
			this.left=left;
		}
		public void setRight(TreeNode right) {
			this.right=right;
		}
	}
	private int size=0;
	private TreeNode root=new TreeNode();
	/**
	 * @return 根节点
	 * init函数设置根结点的值
	 */
	public void init(V value) {
		root.setValue(value);
	}
	/**
	 * @return 树为空则返回True
	 * 	 * 只有一个根节点的数视为空树
	 */
	public boolean isEmpty() {
		return (getLength()==1);
	}
	public int getLength() {
		size=0;
		getLength(root);
		return size;
	}
	private boolean getLength(TreeNode node) {
		if (node!=null) {
			if (getLength(node.left)) {
				size++;
				if (getLength(node.right)) {
					return true;
				}
				return false;
			}
			return false;
		}
		return true;
	}
	/**
	 * @return 返回根节点root
	 */
	public TreeNode getRoot() {
		return root;
	}
	
	/**
	 * @param node
	 * @param value
	 * @param isLeft
	 * @return 给定插入位置以及插入值进行插入，不设置key，返回新结点
	 * 	 * insert返回值设置为结点可以方便后续的插入操作
	 */
	public TreeNode insert(TreeNode node, V value, boolean isLeft) {
		TreeNode treeNode=new TreeNode();
		treeNode.setValue(value);
		if (isLeft) {
			if (node.left!=null) {
				node.left.parent=treeNode;
				treeNode.left=node.left;
			}
			node.left=treeNode;
			treeNode.parent=node;
		}
		else {
			if (node.right!=null) {
				node.right.parent=treeNode;
				treeNode.right=node.right;
			}
			node.right=treeNode;
			treeNode.parent=node;
		}
		return treeNode;	
	}
	/**
	 * @param node
	 * @param key
	 * @param value
	 * @param isLeft
	 * @return 带key插入，返回新结点
	 */
	public TreeNode insert(TreeNode node, K key, V value, boolean isLeft) {
		TreeNode temp=insert(node, value, isLeft);
		temp.setKey(key);
		return temp;
	}
	
	public TreeNode insert(TreeNode node, TreeNode anode, boolean isLeft) {
		if (anode.parent==null) {
			anode.parent=node;
			if (isLeft) {
				node.left=anode;
			}
			else {
				node.right=anode;
			}
			return anode;
		}
		return null;
	}
	/**
	 * 删除单个结点的操作涉及到调整二叉树的结构
	 * 需要根据实际情况在使用的时候@override
	 * 在二叉树的实现中先置为空
	 */
	public void remove() {
		
	}
	/**
	 * @return
	 * 从根结点开始销毁已经存在的二叉树
	 */
	public boolean destroy() {
		if (!this.isEmpty()) {
			return destroy(root);
		}
		return false;
	}
	/**
	 * @param node
	 * @return
	 * 调用递归算法按中序遍历删除以给定结点为指针的子树
	 * 怎么回收内存???
	 */
	private boolean destroy(TreeNode node) {
		if (node!=null) {
			if (destroy(node.left)) {
				if (destroy(node.right)) {
					node.setValue(null);
					return true;
				}
				return false;
			}
			return false;
		}
		return true;
	}
	public boolean inorderTraverse() {
		if (!isEmpty()) {
			return inorderTraverse(root);
		}
		return false;
	}
	/**
	 * @param node
	 * @return
	 */
	private boolean inorderTraverse(TreeNode node) {
		if (node!=null) {
			if (inorderTraverse(node.left)) {
				System.out.print(node.value);
				if (inorderTraverse(node.right)) {
					return true;
				}
				return false;
			}
			return false;
		}
		return true;
	}
	

	
}