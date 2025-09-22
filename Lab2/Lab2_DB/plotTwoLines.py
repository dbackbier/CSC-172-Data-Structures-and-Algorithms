import matplotlib.pyplot as plt

x = [1000, 2000, 4000, 8000, 16000, 32000, 1000000] # 1Mints.txt won't be used for ThreeSum and ThreeSumFast because it will take too long
y1 = [0.0, 0.0, 0.0, 0.0, 0.1, 0.3, 638.5] # Data from TwoSum algorithm
y2 = [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.1] # Data from TwoSumFast algorithm

plt.plot(x, y1, marker="o", label="y = x²")   # first line, using y1 values
plt.plot(x, y2, marker="s", label="y = x")    # second line, using y2 values

plt.xscale("log") # scale n values logarithmically

plt.xlabel("n")
plt.ylabel("T(n)")
plt.title("n vs T(n) TwoSum and TwoSumFast") # Title for TwoSum and TwoSumFast, changes depending on which algorithms are being graphed

plt.show()