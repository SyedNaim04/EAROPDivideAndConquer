public class DivideAndConquer {

    // Class variables to track the best route
    static int minTotalCost = Integer.MAX_VALUE;
    static String bestRoute = "";

    // The main method allows you to run the code in BlueJ and get your output screenshot
    public static void main(String[] args) {
        int[][] costMatrix = {
            {0,  15, 25, 35}, // Row 0: From Hospital
            {15,  0, 30, 28}, // Row 1: From Location B
            {25, 30,  0, 20}, // Row 2: From Location C
            {35, 28, 20,  0}  // Row 3: From Location D
        };
        
        System.out.println("Calculating routes...");
        String result = divideAndConquerEAROP(costMatrix);
        System.out.println("\n--- FINAL RESULT ---");
        System.out.println(result);
    }

    // ==========================================
    // Divide and Conquer Route Optimization
    // ==========================================
    public static String divideAndConquerEAROP(int[][] dist) {
        int n = dist.length;
        boolean[] visited = new boolean[n];
        visited[0] = true; 
        
        minTotalCost = Integer.MAX_VALUE;
        bestRoute = "";
        StringBuilder startingPath = new StringBuilder("Hospital");
        
        divideAndConquerHelper(0, visited, 0, dist, n, startingPath);
        
        return "Optimal Route: " + bestRoute + "\nMinimum Cost: " + minTotalCost;
    }

    // Divide and Conquer Helper Method
    private static int divideAndConquerHelper(
            int pos, 
            boolean[] visited, 
            int currentCost, 
            int[][] dist, 
            int n, 
            StringBuilder path) {
        
        // BASE CASE
        if (allVisited(visited)) {
            int returnCost = dist[pos][0];
            int totalCost = currentCost + returnCost;
            
            if (totalCost < minTotalCost) {
                minTotalCost = totalCost;
                bestRoute = path.toString() + " -> Hospital";
            }
            return totalCost;
        }

        int localMin = Integer.MAX_VALUE;

        // DIVIDE STEP
        for (int i = 1; i < n; i++) {
            if (!visited[i]) {
                visited[i] = true;
                int travelCost = dist[pos][i];
                
                String locName = (i == 1) ? "B" : (i == 2) ? "C" : "D";
                int originalLength = path.length();
                path.append(" -> ").append(locName);
                
                int branchCost = divideAndConquerHelper(i, visited, currentCost + travelCost, dist, n, path);
                localMin = Math.min(localMin, branchCost);
                
                // BACKTRACK
                visited[i] = false;
                path.setLength(originalLength); 
            }
        }
        return localMin;
    }

    // Check whether all emergency locations have been visited
    private static boolean allVisited(boolean[] visited) {
        for (boolean v : visited) {
            if (!v) {
                return false;
            }
        }
        return true;
    }
}