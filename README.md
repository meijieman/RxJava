ReactiveX 是 Reactive Extensions 的简写，一般简写为 Rx。

Single
Observable 的变种，不同于 Observable 的是，Single 总是只发射一个值，或一个错误通知。

Subject
同时充当了 Observer 和 Observable 的角色。
针对不同的场景共有4中类型的 Subject
AsyncSubject
BehaviorSubject
PublishSubject
ReplaySubject

Scheduler
调度器
用于在多线程中指定操作符或特定的 Observable 在特定的调度器上执行

ObserveOn 指示一个 Observable 在一个特定的调度器上调用观察者的 onNext, OnError 或 onCompleted
SubscribeOn 指示 Observable 将全部的处理过程放在特定的调度器上执行。

Schedulers.computation()
Schedulers.from(executor)
Schedulers.immediate()
Schedulers.io()
Schedulers.newThread()
Schedulers.trampoline()

某些 Observable 操作符允许设置用于操作执行的调度器











