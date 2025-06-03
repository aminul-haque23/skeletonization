import numpy as np
import matplotlib.pyplot as plt

def load_image(file_path):
    with open(file_path, 'r') as f:
        lines = f.read().strip().split('\n')
    
    header = list(map(int, lines[0].split()))
    rows, cols, minval, maxval = header
    pixels = list(map(int, ' '.join(lines[1:]).split()))

    if len(pixels) != rows * cols:
        raise ValueError(f"{file_path}: Expected {rows * cols} pixels, got {len(pixels)}")

    image = np.array(pixels).reshape((rows, cols))
    return image, minval, maxval

# Load both images
input_image, in_min, in_max = load_image('input.txt')
output_image, out_min, out_max = load_image('skeleton_image.txt')

# Display them side by side
fig, axes = plt.subplots(1, 2, figsize=(10, 6))

axes[0].imshow(input_image, cmap='gray', vmin=in_min, vmax=in_max)
axes[0].set_title('Input Image')
axes[0].axis('off')

axes[1].imshow(output_image, cmap='gray', vmin=out_min, vmax=out_max)
axes[1].set_title('Output Image')
axes[1].axis('off')

plt.tight_layout()
plt.show()
