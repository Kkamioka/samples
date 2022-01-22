sweets = []
sweets.append('ティラミス')     # 要素を追加
print(sweets)                   # 出力：ティラミス
sweets.append('チョコエクレア')  # 要素を追加
print(sweets)

# リスト
sweets = ['ティラミス', 'チョコエクレア', 'クレームブリュレ']
print(sweets[0])   # 出力：ティラミス
print(sweets[1])   # 出力：'チョコエクレア'
print(sweets[2])   # 出力：'クレームブリュレ'

for count in [0, 1, 2, 3, 4]:
    print(count)

# タプル
tuple= ('設定1', '設定2', '設定3', '設定4')
for t in tuple:
	print(t)

# 辞書
menu = {'朝食' : 'シリアル',
        '昼食' : '牛丼',
        '夕食' : 'トマトのパスタ' }
print(menu) # {'朝食': 'シリアル', '昼食': '牛丼', '夕食': 'トマトのパスタ'}
print(menu['朝食'])
menu['おやつ'] = 'ドーナッツ'
print(menu)
menu['おやつ'] = 'いちご大福'
print(menu)
del menu['おやつ']
print(menu)

setting= {
          '設定1' : 'メール送信',
          '設定2' : 'リクエスト',
          '設定3' : 'レスポンス'
        }
for key in setting:
    print(key)
    
val = setting.values()
print(val)

val = setting.items()
print(val)

for key, value in setting.items():
    print('「{}」は{}です。'.format(key, value))
