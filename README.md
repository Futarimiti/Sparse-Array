# Sparse Array
Imagine a 2D int array storing few data and a lot of 0, which are meaningless as default values but still take memory spaces.</br>
Great memory waste right? Sparse array can do the compress job and help you save space.

### How sparse array work
Sparse array is an alternative way of storing an array, usually used when an array stores a lot of 0 or other repeating values.

The sparse array records 2 things:
* number of rows, columns and valid (non-0) values
* row & col of each non-zero values

An example is:

![Example](./pic/sparse_arr_example.png)
While the original array on the left takes 42 cells, the sparse array on the right takes 27.

### How to convert to sparse array
1. Traverse through the original array to get total number of valid data `sum`. Size of sparse array is immutable so this is required to calculate how many rows and cols are needed.

2. Then you can create the sparse array, which should have `sum + 1` rows, with an extra top row storing total rows, cols and number of values. Dimension of the array varies number of cols, for 2D array it should be 3.

3. Now store the values with corresponding rows and cols into the sparse array.

### How to convert back
1. Read top row of the sparse array to get number of rows and cols of the original array.

2. Create the array using the given size and fill with the default value.

3. Read other rows and assign values to cells.

### Code Example
First we have a 2D int array `original` with only two valid data:
```java
int[][] original = new int[11][11];
original[1][2] = 1;
original[2][3] = 2;
```

Traverse through `original` to look for total number of valid data:
```java
int sum = 0;

for (int[] row : original)
{
	for (int val : row)
	{
		if (val != 0) sum++;
	}
}
```

Create sparse array using `sum`:
```java
int[][] sparseArr = new int[sum + 1][3];
```

Assign info of `original` to top row:
```java
sparseArr[0][0] = original.length;
sparseArr[0][1] = original[0].length;
sparseArr[0][2] = sum;
```

Traverse through `original` again, this time for assigning values:
```java
int count = 1; // specifies row in $sparseArr
for (int row = 0 ; row < original.length ; row++)
{
	for (int col = 0 ; col < original[row].length ; col++)
	{
		int val = original[row][col];

		if (val != 0)
		{
			sparseArr[count][0] = row;
			sparseArr[count][1] = col;
			sparseArr[count][2] = val;
			count++;
		}

		if (count == sum + 1) break;
		// last row reached -> all valid data assigned, no need to continue
	}
}
```

Comparison of original array and sparse array:</br>
```
-- original --
0	0	0	0	0	0	0	0	0	0	0
0	0	1	0	0	0	0	0	0	0	0
0	0	0	2	0	0	0	0	0	0	0
0	0	0	0	0	0	0	0	0	0	0
0	0	0	0	0	0	0	0	0	0	0
0	0	0	0	0	0	0	0	0	0	0
0	0	0	0	0	0	0	0	0	0	0
0	0	0	0	0	0	0	0	0	0	0
0	0	0	0	0	0	0	0	0	0	0
0	0	0	0	0	0	0	0	0	0	0
0	0	0	0	0	0	0	0	0	0	0

-- sparse --
11   11   2
1    2    1
2    3    2
```
