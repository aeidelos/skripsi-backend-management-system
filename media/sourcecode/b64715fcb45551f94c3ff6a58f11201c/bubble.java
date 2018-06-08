import java.util.stream.IntStream;
public class bubble
{
    public static void main(String[]x) {
        
        int arr[] = new int[5];
        arr[0] = 3;
        arr[1] = 11;
        arr[2] = 7;
        arr[3] = 10;
        arr[4] = 20;
        bubbleSort(arr);
        for(int i = 0; i<arr.length; i++){
            System.out.println(arr[i]);
        }
    
    }
    static void bubbleSort(int[] arr) {
    int n = arr.length;
    IntStream.range(0, n - 1)
    .flatMap(i -> IntStream.range(i + 1, n - i))
    .forEach(j -> {
        if (arr[j - 1] > arr[j]) {
            int temp = arr[j];
            arr[j] = arr[j - 1];
            arr[j - 1] = temp;
            }
     });
}
}
