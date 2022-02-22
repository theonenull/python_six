# GIT

## 创建仓库

- ```
  #在合适的地方打开git bash，输入以下命令
  $ mkdir learngit
  $ cd learngit
  $ pwd
  
  ```

- ```
  #将该仓库变为git可管理的
  $ git init
  Initialized empty Git repository in /Users/michael/learngit/.git/
  ```

## 提交文件

### 提交到本地

- ```
  #注意：必须在git目录仓库中运行，而且文件必须在该目录下
  git add 文件名
  git commit -m <message> #提交到本地仓库，massage一定要填，否则会有很多问题
  
  ```

- ```
  git status #该命令可看提交状况
  ```

- ```
  git diff #查看修改
  ```

### 连接远程仓库

- ```
  ssh-keygen -t rsa -C "个人邮箱@example.com"
  #一直回车
  #在用户目录中得到ssh，添加到github中
  ```

- ```
  git remote add origin git@github.com:个人github用户名/远程仓库名.git
  ```

- ```
  #第一次推送
  git push -u #-u 来关联两个仓库
  #以后git push就好了
  ```

  # 删除远程库

  如果添加的时候地址写错了，或者就是想删除远程库，可以用`git remote rm <name>`命令。使用前，建议先用`git remote -v`查看远程库信息：

  ```
  $ git remote -v
  origin  git@github.com:michaelliao/learn-git.git (fetch)
  origin  git@github.com:michaelliao/learn-git.git (push)
  ```

  然后，根据名字删除，比如删除`origin`：

  ```
  $ git remote rm origin
  ```

  此处的“删除”其实是解除了本地和远程的绑定关系，并不是物理上删除了远程库。远程库本身并没有任何改动。要真正删除远程库，需要登录到GitHub，在后台页面找到删除按钮再删除。