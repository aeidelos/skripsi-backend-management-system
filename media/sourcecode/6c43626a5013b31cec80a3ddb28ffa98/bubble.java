
public class bubble
{
    public static void main(String[]x) {
        
        int arr[] = new int[3];
        arr[0] = 1;
        arr[1] = 10;
        arr[2] = 3;
        optimizedBubbleSort(arr);
        for(int i = 0; i<arr.length; i++){
            System.out.println(arr[i]);
        }
    
    }
    public static void optimizedBubbleSort(int[] arr) {
        int i = 0, n = arr.length;
        boolean swapNeeded = true;
        while (i < n - 1 && swapNeeded) {
            swapNeeded = false;
            for (int j = 1; j < n - i; j++) {
                if (arr[j - 1] > arr[j]) {
                    int temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                    swapNeeded = true;
                }
            }
            if(!swapNeeded) {
                break;
            }
            i++;
        }
    }   
}
