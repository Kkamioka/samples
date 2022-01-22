#!/usr/bin/env python
# coding: utf-8

# In[1]:


import pandas as pd
df = pd.read_csv('Boston.csv') # csvの読み込み
df.head(2) # 先頭2行の表示


# In[3]:


df.shape


# In[4]:


df2 = df.fillna(df.mean( )) # 列ごとの平均値で欠損値の穴埋め


# In[5]:


dummy = pd.get_dummies(df2['CRIME'], drop_first = True)
df3 = df2.join(dummy) # df2とdummyを列方向に結合
df3 = df3.drop(['CRIME'], axis = 1) # 元のCRIMEを削除

df3.head(2)


# In[6]:


from sklearn.preprocessing import StandardScaler
 # 中身が整数だと、fit_transformで警告になるので、
# float型に変換（省略可能）
df4 = df3.astype('float')
# 標準化
sc = StandardScaler()
sc_df = sc.fit_transform(df4)


# In[7]:


from sklearn.decomposition import PCA


# In[8]:


model = PCA(n_components = 2, whiten = True) # モデル作成


# In[9]:


# モデルに学習させる
model.fit(sc_df)


# In[10]:


# 新規の第１軸（第１主成分とも呼ぶ）の固有ベクトル
print( model.components_[0] )
print('-----')
# 新規の第2軸（第2主成分とも呼ぶ）の固有ベクトル
print(model.components_[1])


# In[11]:


new = model.transform(sc_df)

new_df = pd.DataFrame(new)
new_df.head(3)


# In[12]:


new_df.columns = ['PC1', 'PC2']
# 標準化済の既存データ（numpy)をデータフレーム化
df5 = pd.DataFrame(sc_df, columns = df4.columns)
# 2つのデータフレームを列方向に結合
df6 = pd.concat([df5, new_df], axis=1)


# In[14]:


df_corr = df6.corr() # 相関係数の計算
df_corr.loc[:'very_low', 'PC1':]


# In[16]:


# わかりやすいように変数に代入
pc_corr = df_corr.loc[:'very_low', 'PC1':]

pc_corr['PC1'].sort_values(ascending = False)


# In[17]:


pc_corr['PC2'].sort_values(ascending = False)


# In[15]:


#都市の発展度合いと住環境の良さ
col = ['City', 'Exclusive residential']

new_df.columns = col # 列名の変更

new_df.plot(kind = 'scatter', x = 'City',
    y = 'Exclusive residential') # 散布図


# In[18]:


model = PCA(whiten = True)

# 学習と新規軸へのデータの当てはめを一括で行う
tmp = model.fit_transform(sc_df)
tmp.shape


# In[19]:


model.explained_variance_ratio_ # 寄与率


# In[20]:


ratio = model.explained_variance_ratio_ # 寄与率のデータ集合

array = [] # 第N列までの累積寄与率を格納するリスト
for i in range(len(ratio)):
# 累積寄与率の計算
    ruiseki = sum(ratio[0:(i+1)])

    array.append(ruiseki) # 累積寄与率の格納

# 第N列の累積寄与率を折れ線グラフ化
pd.Series(array).plot(kind = 'line')


# In[21]:


thred = 0.8 # 累積寄与率のしきい値
for i in range(len(array)):
 # 第(i + 1)列の累積寄与率がthredより大きいかチェック
    if array[i] >= thred:
        print(i + 1)
        break


# In[22]:


model = PCA(n_components=6, whiten = True)

model.fit (sc_df) # 学習

# 元データを新規の列（6列）に当てはめる
new = model.transform(sc_df)


# In[23]:


# 主成分分析の結果をデータフレームに変換
col = ['PC1', 'PC2', 'PC3', 'PC4', 'PC5', 'PC6']
new_df2 = pd.DataFrame(new, columns = col)

# データフレームをcsvファイルとして保存
new_df2.to_csv('boston_pca.csv', index = False)


# In[ ]:





# In[24]:


df = pd.read_csv('cinema.csv')
df = df.drop('cinema_id', axis = 1)
# 欠損値補完
df = df.fillna(df.mean())
# 可能なら外れ値の確認もするが
# 今回は割愛


# In[25]:


from sklearn.preprocessing import StandardScaler
sc = StandardScaler()
sc_df = sc.fit_transform(df)
sc_df = pd.DataFrame(sc_df, columns = df.columns)


# In[27]:


# 累積寄与率を調べる
from sklearn.decomposition import PCA
model = PCA(whiten = True)
model.fit(sc_df)

total = []
for i in model.explained_variance_ratio_:
    if len(total) == 0:
        total.append(i)
    else:
        tmp = total[-1] + i
        total.append(tmp)
total


# In[28]:


model = PCA(whiten=True, n_components = 3)
model.fit(sc_df)
new = pd.DataFrame(model.transform(sc_df), columns=['pc1',
'pc2', 'pc3'])
new_df = pd.concat([new, sc_df], axis = 1)
cor_df = new_df.corr()
cor_df.loc['pc1':'pc3', 'SNS1':]


# In[ ]:




