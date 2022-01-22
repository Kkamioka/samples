#!/usr/bin/env python
# coding: utf-8

# In[2]:


import pandas as pd


# In[3]:


data = {
  '松田の労働時間' : [160, 160], # 松田の労働時間列の作成
  '浅木の労働時間' : [161, 175] # 浅木の労働時間列の作成
}

df = pd.DataFrame(data)
df # dfの表示


# In[4]:


#セルの途中の場合、print関数を
#利用しないと表示できない
print(type(df))

df.shape


# In[5]:


df.index = ['4月', '5月'] # dfのインデックスを変更
df # 表示


# In[6]:


df.columns = ['松田の労働(h)', '浅木の労働(h)'] # 列名の変更
df # 表示


# In[7]:


print(df.index) # インデックスの参照
print(df.columns) # カラムの参照


# In[8]:


data = [
 [160, 161],
 [160, 175]
    ]

df2 = pd.DataFrame(data, index = ['4月', '5月'], columns =
    ['松田の労働', '浅木の労働'])


# In[9]:


# pandasは別名pdでインポート済み
# KvsT.csvファイルを読み込んで、データフレームに変換
df = pd.read_csv('KvsT.csv')
# 先頭3行だけ表示
df.head(3)


# In[10]:


#身長列だけを参照
df['身長']


# In[11]:


# 抜き出したい列名の文字列リストを作成
col = ['身長', '体重']
# 身長列と体重列のみを抜き出す
df[col]


# In[17]:


type(df['派閥'])


# In[18]:


# 特徴量の列を参照して xに代入
xcol = ['身長', '体重', '年代']
x = df[xcol]
x


# In[19]:


# 正解データ（派閥）を参照して、tに代入
t = df['派閥']
t


# In[ ]:





# In[20]:


from sklearn import tree


# In[21]:


# モデルの準備（未学習）
model = tree.DecisionTreeClassifier(random_state = 0)
# 学習の実行（x、tは事前に定義済みの特徴量と正解ラベル）
model.fit(x,t)


# In[22]:


# 身長170cm、体重70kg、年齢20代のデータ（新しいデータ）を
# 2次元リストで作成
taro = [[170, 70, 20]]

 # taroがどちらに分類されるか予測
model.predict(taro)


# In[23]:


matsuda=[172,65,20]
asagi=[158,48,20]

new_data=[matsuda,asagi]
model.predict(new_data)


# In[24]:


#正解率の計算
model.score(x, t)


# In[25]:


import pickle

with open('KinokoTakenoko.pkl', 'wb') as f:
    pickle.dump(model, f)


# In[26]:


import pickle

with open('KinokoTakenoko.pkl', 'rb') as f:
    model2 = pickle.load(f)


# In[27]:


suzuki = [[180,75,30]]
model2.predict(suzuki)


# # 演習問題

# In[28]:


data = {
    'データベースの試験得点':[70,72,75,80],
    'ネットワークの試験得点':[80,85,79,92]
}
df = pd.DataFrame(data)


# In[29]:


df.index = ['一郎','次郎','三郎','太郎']
df#dfの参照


# In[30]:


df2 = pd.read_csv('ex1.csv')


# In[31]:


df2.index # インデックスの一覧表示


# In[32]:


df2.columns#列名の一覧表示


# In[31]:


col = ['x0','x2']
df2[col]


# In[ ]:




