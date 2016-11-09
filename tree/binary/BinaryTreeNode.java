package tree.binary;
  
  public class BinaryTreeNode {
      
      private String data;
      private BinaryTreeNode left, right;
      
      BinaryTreeNode (String s) {
          data = s;
          left = null;
          right = null;
      }
      
      public void add (String s, String child) {
          if (child.equals("L")) {
              left = new BinaryTreeNode(s);
          } else if (child.equals("R")) {
              right = new BinaryTreeNode(s);
          } else {
              throw new IllegalArgumentException();
          }
      }
      
      public BinaryTreeNode getChild (String child) {
          return (child.equals("L")) ? left : right;
      }
      
      public String getString () {
          return data;
      }
      
      public void doubleTree () {
          postOrderTraverse(this);
      }
      
      public boolean sameTree (BinaryTreeNode n1, BinaryTreeNode n2) {
          return preOrderTraverse(n1, n2);
      }
      
      /*
       * Private Methods
       */
      
      //Post Traversal method (the string tells us whether we are comparing two nodes or copying them)
      private void postOrderTraverse (BinaryTreeNode currentNode) {
    	  //Recurse Left, Recurse Right
    	  if (currentNode != null) {
    		  postOrderTraverse(currentNode.left);
    		  postOrderTraverse(currentNode.right);
    		  
        	  //Duplicate Current node
        	  BinaryTreeNode originalLeft = currentNode.left;
        	  currentNode.add(currentNode.data, "L");
        	  currentNode.left.left = originalLeft;//Fix missing references
    	  }
    	  
      }
      
      private boolean preOrderTraverse (BinaryTreeNode n1, BinaryTreeNode n2) {
    	  //Check current nodes
    	  if (n1 == null && n2 == null) {
    		  return true;
    	  }
    	  if (n1 == null || n2 == null) {
    		  return false;
    	  }
    	  if (n1.getString().equals(n2.getString())) {
    		  //Recurse Left, Recurse Right
    		  return(preOrderTraverse(n1.left, n2.left) && preOrderTraverse(n1.right, n2.right));
    	  } else {
    		  return false;
    	  }
      }
  }