#!/usr/bin/env python
# coding: utf-8

# In[8]:


import pandas as pd
df = pd.read_csv('Wholesale.csv')
df.head(3)


# In[9]:


df.isnull().sum()


# In[10]:


df = df.drop(['Channel', 'Region'], axis = 1)


# In[11]:


from sklearn.preprocessing import StandardScaler

sc = StandardScaler()
sc_df = sc.fit_transform(df)
sc_df = pd.DataFrame(sc_df, columns=df.columns)


# In[12]:


from sklearn.cluster import KMeans


# In[13]:


model = KMeans(n_clusters = 3, random_state = 0)


# In[14]:


# モデルに学習させる
model.fit(sc_df)


# In[17]:


model.labels_


# In[18]:


sc_df['cluster'] = model.labels_
sc_df.head(2)


# In[19]:


sc_df.groupby('cluster').mean()


# In[20]:


get_ipython().magic(u'matplotlib inline')
cluster_mean = sc_df.groupby('cluster').mean()
cluster_mean.plot(kind = 'bar')


# In[21]:


sse_list = []
# クラスタ数2～30でSSEを調べる
for n in range(2, 31):
    model = KMeans(n_clusters = n, random_state = 0)
    model.fit(sc_df)
    sse = model.inertia_ # SSEの計算
    sse_list.append(sse)
sse_list


# In[22]:


se = pd.Series(sse_list)
num = range(2, 31) # range関数で2～30の整数列を作る
se.index = num # シリーズのインデックスを変更
se.plot(kind = 'line')


# In[24]:


model = KMeans(n_clusters = 5, random_state = 0)
model.fit(sc_df)
sc_df['cluster'] = model.labels_
sc_df.to_csv('clustered_Wholesale.csv', index = False)


# In[ ]:





# In[25]:


import pandas as pd
df = pd.read_csv('Survived.csv')


# In[26]:


df = df.drop(['PassengerId', 'Ticket', 'Cabin', 'Embarked'], axis = 1)


# In[27]:


df = df.fillna(df.mean())


# In[28]:


dummy = pd.get_dummies(df['Sex'], drop_first = True)
df = pd.concat([df, dummy], axis = 1)
df = df.drop('Sex', axis = 1)


# In[29]:


from sklearn.covariance import MinCovDet

mcd = MinCovDet(random_state=0)
mcd.fit(df)

maha_dis = mcd.mahalanobis(df)
tmp = pd.Series(maha_dis)
tmp.plot(kind = 'box')


# In[30]:


num = tmp[ tmp > 10000 ].index

df = df.drop(num)


# In[31]:


from sklearn.preprocessing import StandardScaler

sc = StandardScaler()
sc_df = sc.fit_transform(df)

df2 = pd.DataFrame(sc_df, columns = df.columns)


# In[32]:


from sklearn.cluster import KMeans

model = KMeans(n_clusters = 2, random_state = 0)
model.fit(df2)

df2['cluster'] = model.labels_


# In[33]:


get_ipython().magic(u'matplotlib inline')

c = df2.groupby('cluster').mean()
c.plot(kind = 'bar')


# In[ ]:




