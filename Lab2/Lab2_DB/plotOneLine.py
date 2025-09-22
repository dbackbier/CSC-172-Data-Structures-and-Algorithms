import matplotlib.pyplot as plt

x = [1000, 2000, 4000, 8000, 16000, 32000, 1000000] # 1Mints.txt won't be used for ThreeSum and ThreeSumFast because it will take too long
y = [0.0, 0.0, 0.0, 0.0, 0.1, 0.3, 638.5] # Data from TwoSum algorithm

plt.plot(x, y, marker="o", linestyle="-")

plt.xscale("log") # scale n values logarithmically

plt.xlabel("n")
plt.ylabel("T(n)")
plt.title("n vs T(n) TwoSum") #Title for TwoSum, changes depending on which algorithm is being graphed

plt.show()