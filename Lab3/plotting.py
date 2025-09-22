import matplotlib.pyplot as plt

x = [1000, 2000, 4000, 8000, 16000, 32000, 1000000]
default = [0,0,0,0,0,1,6] # Arrays.sort() data
bubble = [1,2,15,49,201,795,391101] # Bubble Sort data
selection = [2,7,15,34,152,597,651894] # Selection Sort data
insertion = [0,0,0,0,1,1,2] # Insertion Sort data
merge = [1,0,1,2,3,11,55] # Merge Sort data
quick = [0,0,0,0,1,2,35] # Quick Sort data

plt.plot(x, default, marker="s", label="Arrays.sort()")
plt.plot(x, bubble, marker="s", label="Bubble Sort")
plt.plot(x, selection, marker="s", label="Selection Sort")
plt.plot(x, insertion, marker="s", label="Insertion Sort")
plt.plot(x, merge, marker="s", label="Merge Sort")
plt.plot(x, quick, marker="s", label="Quick Sort")

plt.xscale("log") # scale n values logarithmically

plt.xlabel("n")
plt.ylabel("T(n)")
plt.legend()
plt.title("n vs T(n) for Array: d") # Title for Array a, changes depending on which array is being graphed

plt.show()