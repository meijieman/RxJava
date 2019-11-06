
* onErrorReturn
让Observable遇到错误时发射一个特殊的项并且正常终止。

* onErrorResumeNext
让Observable在遇到错误时开始发射一个新的 Observable 的数据序列

* onExceptionResumeNext
Throwable 的子类为 Exception 和 Error
和onErrorResumeNext类似，onExceptionResumeNext方法返回一个镜像原有Observable行为的新Observable，也使用一个备用的Observable，不同的是，如果onError收到的Throwable不是一个Exception，它会将错误传递给观察者的onError方法，不会使用备用的Observable。


* retry

* retryWhen


* blockingFirst


* concatWith


项目实例

1. 网络请求失败后重试，重试2次

2. 网络请求失败后延时2s重试，重试2次




* zip


* concat
组合多个被观察者一起发送数据，合并后 按发送顺序串行执行

* concatArray

* concatDelayError

* merge
组合多个被观察者一起发送数据，合并后 按时间线并行执行

* mergeArray

* mergeDelayError

* reduce
把一个 Observable 的一系列发送事件聚合成一个事件

* startWith/startWithArray
在发送 Observable 之前先发送 startWith 的 Observable

* count
计数


## Subject
既是 Observable 也是 Observer

PublishSubject 接收到订阅之后的所有数据。
ReplaySubject 接收到所有的数据，包括订阅之前的所有数据和订阅之后的所有数据。
BehaviorSubject 接收到订阅前的最后一条数据和订阅后的所有数据。
AsyncSubject 不管在什么位置订阅，都只接接收到最后一条数据


CompletableSubject 只发送Observer发射完毕数据，即：只发送onCompelted()
MaybeSubject
SingleSubject
UnicastSubject 只能有一个观察者订阅，如果有多个观察者订阅，程序会报错。











