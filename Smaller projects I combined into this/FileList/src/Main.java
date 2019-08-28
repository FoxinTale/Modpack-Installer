import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        String dirName = "C:\\Users\\Alyan\\Downloads\\Modpack\\mods\\";
        String dirTwo = "C:\\Users\\Alyan\\Downloads\\Updates\\";
        ArrayList<String> contents = new ArrayList<>();
        ArrayList<String> contentsTwo = new ArrayList<>();
        
        Set<Object> listOne = new HashSet<Object>(); 
        // a.addAll(Arrays.asList(new Integer[] {1, 3, 2, 4, 8, 9, 0})); 
        Set<Object> listTwo = new HashSet<Object>(); 
        // b.addAll(Arrays.asList(new Integer[] {1, 3, 7, 5, 4, 0, 7, 5})); 

        try {
			Files.list(new File(dirName).toPath())
			        .forEach(path -> {
			           //System.out.println(path.getFileName());
			        	contents.add(path.getFileName().toString());
			        });
			
			Files.list(new File(dirTwo).toPath())
	        .forEach(item -> {
	           //System.out.println(path.getFileName());
	        	contentsTwo.add(item.getFileName().toString());
	        });
			
			
			// System.out.println(Arrays.toString(contents.toArray()));
			// System.out.println(Arrays.toString(contentsTwo.toArray()));
			
			Object[] contArrOne = contents.toArray();
			Object[] contArrTwo = contentsTwo.toArray();
			
			listOne.addAll(contents);
			listTwo.addAll(contentsTwo);
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public static void Union(Object arr1[], Object arr2[], ) {
		 if (m > n)  
	        { 
	            Object[] tempp = arr1; 
	            arr1 = arr2; 
	            arr2 = tempp; 
	  
	            int temp = m; 
	            m = n; 
	            n = temp; 
	        } 
	  
	        // Now arr1[] is smaller 
	        // Sort the first array and print its elements (these two 
	        // steps can be swapped as order in output is not important) 
	        Arrays.sort(arr1); 
	        for (int i = 0; i < m; i++) 
	            System.out.print(arr1[i] + " "); 
	  
	        // Search every element of bigger array in smaller array 
	        // and print the element if not found 
	        for (int i = 0; i < n; i++)  
	        { 
	            if (binarySearch(arr1, 0, m - 1, arr2[i]) == -1) 
	                System.out.print(arr2[i] + " "); 
	        } 
	}

	}
