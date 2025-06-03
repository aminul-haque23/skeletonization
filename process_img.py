from skimage import data
import numpy as np

# Load a sample binary image
image = data.horse()  # Binary image (0s and 1s)

# Get dimensions
numRows, numCols = image.shape

# Create header (minVal = 0, maxVal = 1 for binary)
header = f"{numRows} {numCols} 0 1\n"

# Write to file
with open('input.txt', 'w') as f:
    f.write(header)
    for row in image:
        # Convert boolean or int array to space-separated string of 0s and 1s
        f.write(' '.join(map(str, row.astype(int))) + '\n')