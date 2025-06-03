# Skeletonization Project

This Java program performs skeletonization on binary images using distance transform (either 4-connected or 8-connected) and local maxima extraction. It processes an input file containing a binary image and produces several output files, including the skeletonized image.

## Requirements

- Java Development Kit (JDK) installed
- Python (optional, for generating the skeleton image)

## Compilation

To compile the Java program, run:

```bash
javac HaqueA_Project5_Main.java
```

This will generate the necessary `.class` files.

## Usage

To run the program, use the following command:

```bash
java HaqueA_Project5_Main input.txt 8 prettyPrint.txt skeleton.txt decompressed.txt log.txt
```

- `input.txt`: The input file containing the binary image.
- `8`: The distance choice (4 for 4-connected or 8 for 8-connected).
- `prettyPrint.txt`: File for pretty-printed outputs.
- `skeleton.txt`: File for the skeleton data.
- `decompressed.txt`: File for the decompressed image.
- `log.txt`: File for logging information.

## Input File Format

The input file should contain a header line with four integers: `numRows`, `numCols`, `minVal`, `maxVal`, followed by the binary image data (0s and 1s) in a grid.

Example:

```
5 5 0 1
0 0 0 0 0
0 1 1 1 0
0 1 1 1 0
0 1 1 1 0
0 0 0 0 0
```

This represents a 5x5 binary image with a cross shape.

## Output Files

- `prettyPrint.txt`: Contains pretty-printed versions of the input image, distance transforms, and skeleton.
- `skeleton.txt`: Contains the skeleton data in a list format (row, column, value).
- `decompressed.txt`: Contains the decompressed image after skeletonization and expansion.
- `log.txt`: Contains logging information about the program's execution.

## Generating the Skeleton Image

To visualize the skeleton as an image for the README, use a Python script to read the skeleton data from `skeleton.txt` and generate an image file. Below is an example using Matplotlib:

```python
import matplotlib.pyplot as plt
import numpy as np

# Example: Create a sample array (replace with actual data from skeleton.txt)
skeletonAry = np.zeros((5, 5))
skeletonAry[2, 1:4] = 1  # Horizontal line
skeletonAry[1:4, 2] = 1  # Vertical line

plt.imshow(skeletonAry, cmap='gray')
plt.axis('off')  # Optional: hide axes
plt.savefig('skeleton_image.png')
```

Modify this script to load your actual skeleton data from `skeleton.txt` and adjust the array size based on your input file.

## Example

Here’s an example of a skeletonized image produced by the program:

![Skeleton Image](skeleton_image.png)
