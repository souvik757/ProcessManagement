// -- sorting applied on the basis of arrival time of a process --
public class HeapSort {
    public void heapSort(Process[] arr) {
        int size = arr.length;
        for (int i = size / 2 - 1; i >= 0; i--) {
            heapify(arr, size, i);
        }
        for (int i = size - 1; i >= 0; i--) {
            Process temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            heapify(arr, i, 0);
        }
    }

    private void heapify(Process[] arr, int size, int i) {
        int largestIndex = i;
        int leftIndex    = 2 * i + 1;
        int rightIndex   = 2 * i + 2;

        if (leftIndex < size && arr[leftIndex].ARRIVAL_TIME > arr[largestIndex].ARRIVAL_TIME)
            largestIndex = leftIndex;

        if (rightIndex < size && arr[rightIndex].ARRIVAL_TIME > arr[largestIndex].ARRIVAL_TIME)
            largestIndex = rightIndex;

        if (largestIndex != i) {
            Process temp = arr[i];
            arr[i] = arr[largestIndex];
            arr[largestIndex] = temp;
            heapify(arr, size, largestIndex);
        }
    }
}
