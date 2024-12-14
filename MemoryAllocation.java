import java.util.Arrays;
import java.util.Scanner;

public class MemoryAllocation {

    // Best Fit Algorithm
    static void bestFit(int blockSizes[], int m, int processSizes[], int n) {
        int allocation[] = new int[n];
        Arrays.fill(allocation, -1);

        for (int i = 0; i < n; i++) {
            int bestIdx = -1;
            for (int j = 0; j < m; j++) {
                if (blockSizes[j] >= processSizes[i]) {
                    if (bestIdx == -1 || blockSizes[bestIdx] > blockSizes[j]) {
                        bestIdx = j;
                    }
                }
            }
            if (bestIdx != -1) {
                allocation[i] = bestIdx;
                blockSizes[bestIdx] -= processSizes[i];
            }
        }

        printResults("Best Fit", allocation, processSizes, n, blockSizes, m);
    }

    // Next Fit Algorithm
    static void nextFit(int blockSizes[], int m, int processSizes[], int n) {
        int allocation[] = new int[n];
        Arrays.fill(allocation, -1);
        int j = 0, t = m - 1;

        for (int i = 0; i < n; i++) {
            while (j < m) {
                if (blockSizes[j] >= processSizes[i]) {
                    allocation[i] = j;
                    blockSizes[j] -= processSizes[i];
                    t = (j - 1 + m) % m; // Update endpoint
                    break;
                }
                if (t == j) break; // Full cycle completed
                j = (j + 1) % m;
            }
        }

        printResults("Next Fit", allocation, processSizes, n, blockSizes, m);
    }

    // Print Allocation Results
    static void printResults(String method, int[] allocation, int[] processSizes, int n, int[] blockSizes, int m) {
        System.out.println("\n" + method + " Allocation Results:");
        System.out.println("Process No.\tProcess Size\tBlock No.");
        for (int i = 0; i < n; i++) {
            System.out.print((i + 1) + "\t\t" + processSizes[i] + "\t\t");
            System.out.println(allocation[i] != -1 ? allocation[i] + 1 : "Not Allocated");
        }
        System.out.println("\nRemaining Free Memory in Each Block:");
        for (int i = 0; i < m; i++) {
            System.out.println("Block " + (i + 1) + ": " + blockSizes[i]);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of memory blocks: ");
        int m = sc.nextInt();
        int[] blockSizes = new int[m];
        System.out.println("Enter block sizes:");
        for (int i = 0; i < m; i++) blockSizes[i] = sc.nextInt();

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();
        int[] processSizes = new int[n];
        System.out.println("Enter process sizes:");
        for (int i = 0; i < n; i++) processSizes[i] = sc.nextInt();

        System.out.println("\nChoose an allocation method:\n1. Best Fit\n2. Next Fit");
        int choice = sc.nextInt();
        switch (choice) {
            case 1 -> bestFit(Arrays.copyOf(blockSizes, m), m, processSizes, n);
            case 2 -> nextFit(Arrays.copyOf(blockSizes, m), m, processSizes, n);
            default -> System.out.println("Invalid choice!");
        }

        sc.close();
    }
}
