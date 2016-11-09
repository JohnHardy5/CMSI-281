package tree.heap;
  
  import java.util.ArrayList;
  
  class BinaryHeap {
      
      ArrayList<Integer> heap;
      
      BinaryHeap () {
          heap = new ArrayList<Integer>();
      }
  
      /*
       * Continues to bubble values up the tree until we
       * find a node that is greater than it
       */
      public void bubbleUp (int index) {
          if (index == 0) {return;}
  
          int parent = getParent(index);
  
          if (heap.get(parent) < heap.get(index)) {
              Integer temp = heap.get(index);
              heap.set(index, heap.get(parent));
              heap.set(parent, temp);
              bubbleUp(parent);
          }
  
      }
      
      public void insert (Integer toInsert) {
          heap.add(toInsert);
          bubbleUp(heap.size() - 1);
      }
  
      // Traversal helpers
      public int getParent (int index) {
          return (index - 1) / 2;
      }
      
      public int getChild (int index, char child) {
          int result = (index * 2) + 1;
          if (child == 'R') {
              result++;
          }
          return result;
      }
      
      public void print () {
          for (int i = 0; i < heap.size(); i++) {
              System.out.println(heap.get(i));
          }
      }
      
      public ArrayList<Integer> getSortedElements () {
          ArrayList<Integer> result = (ArrayList<Integer>) heap.clone();
          int count = result.size() - 1;
          while(count > 1) {
              Integer temp = result.get(count);
        	  result.set(count, result.get(0));
              result.set(0, temp);
        	  count--;
        	  reheapify (result, 0, count);
          }
          return result;
      }
      
      /*
       * Private Methods
       */
      //Replaces the root with its greatest child recursively
      private void reheapify (ArrayList<Integer> reheap, int index, int count) {
    	  int greatestChild = findGreatestChild(reheap, index, count);
    	  bubbleUp(greatestChild);
    	  if (greatestChild < count) {//if the greatest child did not exist, stop reheapifying
    		  reheapify(reheap, greatestChild, count);
    	  }
      }
      
      private int findGreatestChild (ArrayList<Integer> reheap, int parent, int count) {
    	  int greatestChild;
    	  int leftChild = getChild(parent, 'L');
    	  leftChild = (leftChild < count) ? leftChild : count;//if child does not exist, set it to the next available item
    	  int rightChild = getChild(parent, 'R');
    	  rightChild = (rightChild < count) ? rightChild : count;
    	  if (reheap.get(leftChild) > reheap.get(rightChild)) {
    		  greatestChild = leftChild;
    	  } else {
    		  greatestChild = rightChild;
    	  }
    	  return greatestChild;
      }
  }