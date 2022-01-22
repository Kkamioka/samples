#!/usr/bin/env python
# coding: utf-8

# In[1]:


import pandas as pd
from sklearn import tree
from sklearn.model_selection import train_test_split
get_ipython().magic(u'matplotlib inline')
df = pd.read_csv('Survived.csv')
df.head(2) # 先頭2行の確認


# In[5]:


df['Survived'].value_counts()


# In[7]:


df.isnull().sum()


# In[8]:


df.shape


# In[10]:


# Age列を平均値で穴埋め
df["Age"] = df["Age"].fillna(df["Age"].mean())
# Embarked列を最頻値で穴埋め
df["Embarked"] = df['Embarked'].fillna(df['Embarked'].mode())


# In[11]:


# 特徴量として利用する列のリスト
col = ['Pclass','Age','SibSp','Parch','Fare']

x = df[col]
t = df['Survived']


# In[12]:


x_train,x_test,y_train,y_test = train_test_split(x,t,
test_size = 0.2,random_state = 0)
# x_trainのサイズの確認
x_train.shape


# In[13]:


model = tree.DecisionTreeClassifier(max_depth = 5,
 random_state = 0,class_weight ='balanced')

model.fit(x_train,y_train) # 学習


# In[14]:


model.score(X = x_test,y = y_test)


# In[15]:


def learn(x,t,depth=3):
    x_train,x_test,y_train,y_test = train_test_split(x,
        t,test_size = 0.2,random_state = 0)
    model = tree.DecisionTreeClassifier(max_depth =depth,random_state = 0,class_weight="balanced")
    model.fit(x_train,y_train)

    score=model.score(X=x_train,y=y_train)
    score2=model.score(X=x_test,y=y_test)
    return round(score,3),round(score2,3),model


# In[16]:


for j in range(1,15): # jは木の深さ jには1～14が入る
    # xは特徴量、tは正解データ
    train_score,test_score,model = learn(x,t,depth = j)
    sentence="訓練データの正解率{}"
    sentence2="訓練データの正解率{}"
    total_sentence='深さ{}:'+sentence+sentence2
    print(total_sentence.format(j,
    train_score,test_score))


# In[17]:


df2 = pd.read_csv('Survived.csv')
print(df2['Age'].mean()) # 平均値の計算
print(df2['Age'].median()) # 中央値の計算


# In[18]:


df2.head()


# In[ ]:





# In[19]:


df2.groupby('Survived').mean()['Age']


# In[20]:


df2.groupby('Pclass').mean()['Age']


# In[21]:


pd.pivot_table(df2,index = 'Survived',columns = 'Pclass',
values = 'Age')


# In[22]:


pd.pivot_table(df2,index = 'Survived',columns = 'Pclass',
values = 'Age',aggfunc = max)


# In[23]:


# Age列の欠損値行を抜き出すのに必要（欠損だとTrue)
is_null = df2['Age'].isnull()

# Pclass 1　に関する埋め込み
df2.loc[(df2['Pclass'] == 1) & (df2['Survived'] == 0)
    &(is_null),'Age'] = 43
df2.loc[(df2['Pclass'] == 1) & (df2['Survived'] == 1)
    &(is_null),'Age'] = 35

# Pclass 2　に関する埋め込み
df2.loc[(df2['Pclass'] == 2) & (df2['Survived'] == 0)
    &(is_null),'Age'] = 33
df2.loc[(df2['Pclass'] == 2) & (df2['Survived'] == 1)
    &(is_null),'Age'] = 25

# Pclass 3　に関する埋め込み
df2.loc[(df2['Pclass'] == 3) & (df2['Survived'] == 0)
    &(is_null),'Age'] = 26
df2.loc[(df2['Pclass'] == 3) & (df2['Survived'] == 1)
    &(is_null),'Age'] = 20


# In[25]:


#特徴量として利用する列のリスト
col = ['Pclass','Age','SibSp','Parch','Fare']
x = df2[col]
t = df2['Survived']

for j in range(1,15): # jは木の深さ
    s1,s2,m = learn(x,t,depth = j)
    sentence='深さ{}:訓練データの精度{}::テストデータの精度{}'
    print(sentence.format(j,s1,s2))


# In[26]:


#性別ごとの各列の平均値を集計。戻り値はデータフレーム
sex = df2.groupby('Sex').mean()
sex['Survived']


# In[27]:


sex['Survived'].plot(kind='bar')


# In[28]:


# 特徴量として利用する列のリスト
col = ['Pclass','Age','SibSp','Parch','Fare','Sex']

x = df2[col]
t = df2['Survived']

train_score,test_score,model = learn(x,t) # 学習


# In[33]:


male = pd.get_dummies(df2['Sex'],drop_first = True)
male


# In[34]:


pd.get_dummies(df2['Sex'])


# In[35]:


pd.get_dummies(df2['Embarked'],drop_first = True)


# In[36]:


embarked = pd.get_dummies(df2['Embarked'],drop_first = False)
embarked.head(3)


# In[37]:


x_tmp=pd.concat([x,male],axis=1)

x_tmp.head(2)


# In[38]:


tmp = pd.concat([x,x],axis = 0)
tmp.shape


# In[39]:


x_new = x_tmp.drop("Sex",axis=1)
for j in range(1,6): # jは木の深さ
 # xは特徴量、tは目的変数
    s1,s2,m = learn(x_new,t,depth = j)
    s='深さ{}:訓練データの精度{}::テストデータの精度{}'
    print(s.format(j,s1,s2))


# In[40]:


s1,s2,model = learn(x_new,t,depth = 5)

# モデルの保存
import pickle
with open('survived.pkl','wb') as f:
    pickle.dump(model,f)


# In[41]:


model.feature_importances_


# In[43]:


#データフレームに変換
pd.DataFrame(model.feature_importances_,index = x_new.columns)


# # 練習問題

# In[44]:


df = pd.read_csv('ex4.csv')
df.head(3)


# In[45]:


df["sex"].mean()


# In[46]:


df.groupby('class').mean()['score']


# In[48]:


pd.pivot_table(df,index='class',columns='sex',values='score')


# In[121]:


dummy = pd.get_dummies(df['dept_id'],drop_first = True)

df2 = pd.concat([df,dummy],axis = 1)

df2 = df2.drop('dept_id',axis = 1)


# In[ ]:




