public class EAROPDivideAndConquer {
    
    // The travel cost matrix provided in the assignment
    // Indices: 0=Hospital, 1=Location B, 2=Location C, 3=Location D
    static int[][] costMatrix = {
        {0,  15, 25, 35}, // Row 0: From Hospital
        {15,  0, 30, 28}, // Row 1: From Location B
        {25, 30,  0, 20}, // Row 2: From Location C
        {35, 28, 20,  0}  // Row 3: From Location D
    };
    
    // Variables to store the ultimate "conquered" results
    static int minTotalCost = Integer.MAX_VALUE;
    static String bestRoute = "";

    public static void main(String[] args) {
        // Array to track which locations have been visited
        boolean[] visited = new boolean[4];
        visited[0] = true; // The ambulance always starts at the Hospital (index 0)
        
        // Start the recursive Divide and Conquer process
        System.out.println("Calculating routes...");
        findRoute(0, visited, 0, "Hospital");
        
        System.out.println("\n--- FINAL RESULT ---");
        System.out.println("Optimal Route: " + bestRoute);
        System.out.println("Minimum Cost: " + minTotalCost);
    }

    // The core recursive method
    public static void findRoute(int currentLocation, boolean[] visited, int currentCost, String path) {
        
        // BASE CASE: Have we visited all locations? (The bottom of the tree)
        if (allVisited(visited)) {
            // Calculate the mandatory return trip to the Hospital (index 0)
            int returnCost = costMatrix[currentLocation][0];
            int totalCost = currentCost + returnCost;
            
            // COMBINE STEP: If this completed branch is cheaper, update our global tracker
            if (totalCost < minTotalCost) {
                minTotalCost = totalCost;
                bestRoute = path + " -> Hospital";
            }
            return; // Step back up the tree
        }

        // DIVIDE STEP: Try visiting all remaining unvisited locations
        for (int nextLocation = 1; nextLocation < 4; nextLocation++) {
            if (!visited[nextLocation]) {
                
                // 1. Mark this location as visited for this branch
                visited[nextLocation] = true;
                
                // 2. Fetch the cost and name for our path string
                int travelCost = costMatrix[currentLocation][nextLocation];
                String locationName = getLocationName(nextLocation);
                
                // 3. RECURSIVE CALL: Dive deeper into this sub-problem
                findRoute(nextLocation, visited, currentCost + travelCost, path + " -> " + locationName);
                
                // 4. BACKTRACK: Unmark this location so the loop can test the next alternative branch
                visited[nextLocation] = false;
            }
        }
    }

    // Helper method to check the Base Case
    public static boolean allVisited(boolean[] visited) {
        for (boolean v : visited) {
            if (!v) {
                return false; // Found an unvisited location
            }
        }
        return true; // All true, meaning all emergencies are handled
    }
    
    // Helper method to make the output readable
    public static String getLocationName(int index) {
        if (index == 1) return "B";
        if (index == 2) return "C";
        if (index == 3) return "D";
        return "Hospital";
    }
}