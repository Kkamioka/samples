#!/usr/bin/env python
# coding: utf-8

# In[2]:


import pandas as pd
get_ipython().magic(u'matplotlib inline')
from sklearn.linear_model import LinearRegression
from sklearn.model_selection import train_test_split


# In[3]:


df = pd.read_csv('Boston.csv')
df.head(2)


# In[4]:


df['CRIME'].value_counts()


# In[5]:


crime = pd.get_dummies(df['CRIME'], drop_first = True)
df2 = pd.concat([df, crime], axis = 1)
df2 = df2.drop(['CRIME'], axis = 1)
df2.head(2)


# In[6]:


train_val, test = train_test_split(df2,test_size = 0.2,
random_state = 0)


# In[7]:


train_val.isnull().sum()


# In[8]:


train_val_mean = train_val.mean() # 各列の平均値の計算
train_val2=train_val.fillna(train_val_mean) # 平均値で穴埋め


# In[9]:


colname = train_val2.columns
for name in colname:
    train_val2.plot(kind = 'scatter', x = name, y = 'PRICE')


# In[10]:


# RMの外れ値
out_line1 = train_val2[(train_val2['RM'] < 6) &
(train_val2['PRICE'] > 40)].index
# PTRATIOの外れ値
out_line2 = train_val2[(train_val2['PTRATIO'] > 18) &
(train_val2['PRICE'] > 40)].index

print(out_line1, out_line2)


# In[11]:


train_val3 = train_val2.drop([76], axis = 0)


# In[12]:


col = ['INDUS', 'NOX', 'RM', 'PTRATIO', 'LSTAT', 'PRICE']

train_val4 = train_val3[col]
train_val4.head(3)


# In[13]:


train_val4.corr()


# In[16]:


train_cor = train_val4.corr()['PRICE']
train_cor


# In[17]:


se = pd.Series([1, -2, 3, -4]) # シリーズの作成

# seの各要素にabs関数を適応させた結果をシリーズ化
se.map(abs)


# In[18]:


abs_cor = train_cor.map(abs)
abs_cor


# In[16]:


# 降順に並べ替える
abs_cor.sort_values(ascending = False)


# In[23]:


col =['RM', 'LSTAT', 'PTRATIO']
x = train_val4[col]
t = train_val4[['PRICE']]

#訓練データと検証データに分割
x_train, x_val, y_train, y_val = train_test_split(x, t,
    test_size = 0.2, random_state = 0)


# In[21]:





# In[24]:


from sklearn.preprocessing import StandardScaler

sc_model_x = StandardScaler()
sc_model_x.fit(x_train)

# 各列のデータを標準化してsc_xに代入
sc_x = sc_model_x.transform(x_train)
sc_x # 表示


# In[25]:


# array 型だと見づらいのでデータフレームに変換
tmp_df = pd.DataFrame(sc_x, columns = x_train.columns)
# 平均値の計算
tmp_df.mean()


# In[26]:


tmp_df.std() # 標準偏差の計算


# In[27]:


sc_model_y = StandardScaler()
sc_model_y.fit(y_train)

sc_y = sc_model_y.transform(y_train)


# In[30]:


model = LinearRegression()
model.fit(sc_x, sc_y)


# In[31]:


model.score(x_val, y_val)


# In[32]:


sc_x_val = sc_model_x.transform(x_val)
sc_y_val = sc_model_y.transform(y_val)
# 標準化した検証データで決定係数を計算
model.score(sc_x_val, sc_y_val)


# In[33]:


# 以下、やってはいけない間違いのコード
sc_model_x2 = StandardScaler()
sc_model_x2.fit(x_val)
sc_x_val = sc_model_x2.transform(x_val)
sc_model_y2 = StandardScaler()
sc_model_y2.fit(y_val)
sc_y_val = sc_model_y2.transform(y_val)
model.score(sc_x_val, sc_y_val)


# In[34]:


def learn(x, t):
    x_train, x_val, y_train, y_val = train_test_split(x, t,
    test_size = 0.2, random_state = 0)
    # 訓練データを標準化
    sc_model_x = StandardScaler()
    sc_model_y = StandardScaler()
    sc_model_x.fit(x_train)
    sc_x_train = sc_model_x.transform(x_train)
    sc_model_y.fit(y_train)
    sc_y_train = sc_model_y.transform(y_train)
    # 学習
    model = LinearRegression()
    model.fit(sc_x_train, sc_y_train)
    #検証データを標準化
    sc_x_val = sc_model_x.transform(x_val)
    sc_y_val = sc_model_y.transform(y_val)
    # 訓練データと検証データの決定係数計算
    train_score = model.score(sc_x_train, sc_y_train)
    val_score = model.score(sc_x_val, sc_y_val)
    return train_score, val_score


# In[ ]:





# In[35]:


x = train_val3.loc[ :, ['RM', 'LSTAT', 'PTRATIO']]
t = train_val3[['PRICE']]
s1,s2 = learn(x, t)
print(s1, s2)


# In[36]:


x = train_val3.loc[ :, ['RM', 'LSTAT', 'PTRATIO','INDUS']]
t = train_val3[['PRICE']]
s1,s2 = learn(x, t)
print(s1, s2)


# In[35]:


x['RM'] ** 2


# In[37]:


# RM2乗のシリーズを新しい列として追加
x['RM2'] = x['RM'] ** 2
# コード8-29で、INDUS列を追加したので削除
x = x.drop('INDUS', axis = 1)
x.head(2)


# In[38]:


# インデックスを2000として新しい行を追加
x.loc[2000] = [10, 7, 8, 100]
print(x.tail(2)) # 確認

# 第8章の本筋には関係ないので削除
x = x.drop(2000, axis = 0)


# In[39]:


s1, s2 = learn(x, t)
print(s1, s2)


# In[40]:


# LSTAT列の2乗を追加
x['LSTAT2'] = x['LSTAT'] ** 2
s1, s2 = learn(x, t)
print(s1, s2)

# PTRATIO列の2乗を追加
x['PTRATIO2'] = x['PTRATIO'] ** 2
s1, s2 = learn(x, t)
print(s1, s2)


# In[41]:


se1 = pd.Series([1, 2, 3])
se2 = pd.Series([10, 20, 30])
se1 + se2 # 対応する各要素を足し算したシリーズ


# In[42]:


x['RM * LSTAT'] = x['RM'] * x['LSTAT']
x.head(2)


# In[43]:


s1, s2 = learn(x, t)
print(s1, s2)


# In[44]:


# 訓練データと検証データを合わせて再学習させるので
# 再度、標準化する
sc_model_x2 = StandardScaler()
sc_model_x2.fit(x)
sc_x = sc_model_x2.transform(x)

sc_model_y2 = StandardScaler()
sc_model_y2.fit(t)
sc_y = sc_model_y2.transform(t)
model = LinearRegression()
model.fit(sc_x, sc_y)


# In[ ]:





# In[ ]:





# In[ ]:





# In[46]:


test2 = test.fillna(train_val.mean()) # 欠損値を平均値で補完
x_test = test2.loc[ :, ['RM','LSTAT', 'PTRATIO'] ]
y_test = test2[['PRICE']]

x_test['RM2'] = x_test['RM'] ** 2
x_test['LSTAT2'] = x_test['LSTAT'] ** 2
x_test['PTRATIO2'] = x_test['PTRATIO'] ** 2

x_test['RM * LSTAT'] = x_test['RM'] * x_test['LSTAT']


# In[47]:


sc_x_test = sc_model_x2.transform(x_test)
sc_y_test = sc_model_y2.transform(y_test)


# In[48]:


model.score(sc_x_test, sc_y_test)


# In[50]:


import pickle
with open('boston.pkl',"wb") as f:
    pickle.dump(model,f)
with open('boston_scx.pkl','wb') as f:
    pickle.dump(sc_model_x2,f)
with open('boston_scy.pkl','wb') as f:
    pickle.dump(sc_model_y2,f)
    


# In[ ]:





# In[ ]:





# In[51]:





# In[ ]:





# In[ ]:




