package SortAlgorithm;

import PROCESS._Process_;

public class MergeSort {
    public static void sort(_Process_[] processes, int low, int high){
        if (low >= high)
            return ;
        int mid = low +(high-low)/2 ;
        sort(processes,low,mid);
        sort(processes,mid+1,high);
        merge(processes,low,mid,high);
    }
    private static void merge(_Process_[] processes, int low, int mid, int high) {
        _Process_[] temp = new _Process_[high-low+1] ;
        int i = low ;
        int j = mid + 1 ;
        int k = 0 ;
        while (i<= mid && j <= high) {
            if (processes[i].arrival_time < processes[j].arrival_time)
                temp[k++] = processes[i++] ;
            else
                temp[k++] = processes[j++] ;
        }
        while (i <= mid)
            temp[k++] = processes[i++] ;
        while (j <= high)
            temp[k++] = processes[j++] ;

        for (k = 0 ; k < temp.length ; k ++)
            processes[k+low] = temp[k] ;
    }
}
