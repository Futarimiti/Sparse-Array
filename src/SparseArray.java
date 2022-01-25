public class SparseArray
{
	public static void main(String[] args)
	{
		int[][] original = new int[11][11];
		original[1][2] = 1;
		original[2][3] = 2;

		int[][] sparseArr = toSparseArray(original);
	}

	public static int[][] toSparseArray(int[][] original)
	{
		// traverse for # of valid values
		int sum = 0;

		for (int[] row : original)
		{
			for (int val : row)
			{
				if (val != 0) sum++;
			}
		}

		// create corresponding sparse array
		int[][] sparseArr = new int[sum + 1][3];

		// assign info to top row: row, col, val#
		sparseArr[0][0] = original.length;
		sparseArr[0][1] = original[0].length;
		sparseArr[0][2] = sum;

		// traverse again to assign sparse array
		int count = 1; // counter, used to specify row in $sparseArr
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

		return sparseArr;
	}
}
