# CSC466

Predict the number of wins for each team every year using K-Nearest Neighbors

Team object - String, ArrayList<Double>
    - all of the stats

Hashmap<Integer, HashMap<String, Team>>
          -year           teams for that year

    
Things to do:
- normalize data
    - pick training and testing data
    - pick 10 chunks and store them in hashmap
    - write the error calculation method for each chunk
    - calculate average errors for chunks on a specific k
    - iterate over k
    - after iterating over all k,find lowest error and pick that k
    - testing data calculations
    - error again to see how accurate the algorithm is
