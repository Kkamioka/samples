# エラーをtxtファイルに保存する例
import traceback

x = {0:1,1:2,3:4}
for n in range(4):
    try:
        print(x[n])
    except Exception as e:
        file = open("./error.txt", 'a')
        file.write(traceback.format_exc())
        file.close()
        
print('done')
