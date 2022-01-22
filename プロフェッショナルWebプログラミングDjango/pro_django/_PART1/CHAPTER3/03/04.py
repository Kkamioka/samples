# tracebackの例
import traceback

x = {0:1,1:2,3:4}
for n in range(4):
    try:
        print(x[n])
    except Exception as e:
        print(traceback.format_exc())

print("done")
