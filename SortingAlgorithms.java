package SortAlgorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

// all algorithms reverse sort to compensate for rotation
public class SortingAlgorithms {
	
	public static void shuffle(int[] data) {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for (Integer value : data) {
			temp.add(value);
		}
		Collections.shuffle(temp);
		for (int i = 0; i < data.length; i++) {
			data[i] = temp.get(i);
		}
	}
	
	public static void bubbleSort(int[] data) {
		for (int i = 0; i < data.length - 1; i++) {
			for (int j = i+1; j < data.length; j++) {
				if (data[i] < data[j]) {
					int temp = data[j];
					data[j] = data[i];
					data[i] = temp;
				}
			}
		}
	}
	
	public static int[] bubbleSortSteps(int[] data){
		int[] steps = new int[2];
		for (int i = 0; i < data.length - 1; i++) {
			for (int j = i+1; j < data.length; j++) {
				if (data[i] < data[j]) {
					steps[0] = data[j];
					steps[1] = data[i];
					return steps;
				}
			}
		}
		
		return steps;
	}
	
	public static void selectionSort(int[] data) {
		for (int i = 0; i < data.length; i++) {
			int big = 0;
			int bigI = 0;
			for (int j = i; j < data.length; j++) {
				if (data[j] >= big) {
					big = data[j];
					bigI = j;
				}
			}
			data[bigI] = data[i];
			data[i] = big;
		}
	}
	
	public static int[] selectionSortSteps(int[] data, int i) {
		int[] steps = new int[2];
		int small = Integer.MAX_VALUE;
		int index = -1;
		for (int j = i; j >= 0; j--) {
			if (data[j] < small) {
				small = data[j];
				index = j;
			}
		}
		steps[0] = i;
		steps[1] = index;
		return steps;
	}
	
	public static void insertionSort(int[] data) {
		for (int i = 1; i < data.length; i++) {
			int j, temp = data[i];
			for (j = i-1; j >= 0 && temp > data[j]; j--) {
				data[j + 1] = data[j];
			}
			data[j + 1] = temp;
		}
	}
	
	// start i at 1
	public static void insertionSortSteps(int[] data, int i) {
		int j, temp = data[i];
		for (j = i + 1; j < data.length && temp < data[j]; j++) {
			data[j - 1] = data[j];
		}
		data[j - 1] = temp;
	}
	
	public static void treeSort(int[] data) {
		// implement
	}
	
	public static void heapSort(int[] data) {
		// implement
	}
	
	public static void quickSort(int[] data) {
		// implement
	}
	
	public static void mergeSort(int[] data) {
		// implement
	}
	
}
