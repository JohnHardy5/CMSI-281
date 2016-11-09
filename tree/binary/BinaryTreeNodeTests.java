package tree.binary;

import static org.junit.Assert.*;

import org.junit.Test;

public class BinaryTreeNodeTests {
	
	@Test
	public void sameTreeTests() {
		BinaryTreeNode root = new BinaryTreeNode("root");
		root.add("branch", "L");
		root.getChild("L").add("leave", "L");
		root.add("leave", "R");
		
		assertEquals(root.getChild("L").getChild("L").getString(), root.getChild("R").getString());
		
		BinaryTreeNode root2 = new BinaryTreeNode("root");
		root2.add("branch", "L");
		root2.getChild("L").add("leave", "L");
		root2.add("leave", "R");
		
		assertTrue(root.sameTree(root, root2));
		assertTrue(root2.sameTree(root2, root));
		
		root2.add("branch", "R");
		root2.getChild("R").add("leave", "L");
		
		assertFalse(root.sameTree(root, root2));
		assertFalse(root2.sameTree(root2, root));
		
		root2 = new BinaryTreeNode("root");
		
		assertFalse(root.sameTree(root, root2));
		assertFalse(root2.sameTree(root2, root));
		
		root = new BinaryTreeNode("root");
		
		assertTrue(root.sameTree(root, root2));
		assertTrue(root2.sameTree(root2, root));
	}

	@Test
	public void doubleTreeTests() {
		BinaryTreeNode root = new BinaryTreeNode("root");
		root.doubleTree();
		
		BinaryTreeNode root2 = new BinaryTreeNode("root");
		root2.add("root", "L");
		
		assertTrue(root.sameTree(root, root2));
		
		root = new BinaryTreeNode("root");
		root.add("leave", "L");
		root.add("leave", "R");
		root.doubleTree();
		
		assertFalse(root.sameTree(root, root2));
		
		root2 = new BinaryTreeNode("root");
		root2.add("root", "L");
		root2.getChild("L").add("leave", "L");
		root2.getChild("L").getChild("L").add("leave", "L");
		root2.add("leave", "R");
		root2.getChild("R").add("leave", "L");
		
		assertTrue(root.sameTree(root, root2));
		
		root = new BinaryTreeNode("root");
		root.add("branch", "L");
		root.getChild("L").add("leave", "L");
		root.add("leave", "R");
		root.doubleTree();
		
		root2 = new BinaryTreeNode("root");
		root2.add("root", "L");
		root2.getChild("L").add("branch", "L");
		root2.getChild("L").getChild("L").add("branch", "L");
		root2.getChild("L").getChild("L").getChild("L").add("leave", "L");
		root2.getChild("L").getChild("L").getChild("L").getChild("L").add("leave", "L");
		root2.add("leave", "R");
		root2.getChild("R").add("leave", "L");
		
		assertTrue(root.sameTree(root, root2));
	}

}
