# MVP Pattern Architecture using Coroutines

It's an Android project with Kotlin implementing/exploring an MVP architectural pattern where the network calls are using  Coroutines in the Data layer with the Repository pattern. 
The Coroutines allow us to keep code more clear and concise. However, I don't know yet how to implement that data response processing that is easy to do with some functions with RxJava, that's something for a further version.

## Architecture Diagram
![App architecture](/images/architecture_android.png)

1. [Data layer:](https://github.com/llanox/AndroidMVPCoroutines/tree/master/app/src/main/java/com/gabo/ramo/data) All data needed for the application comes from this layer using the Repository pattern. Here, you can find the logic for the caching strategy logic as well.
2. [Domain layer:](https://github.com/llanox/AndroidMVPCoroutines/tree/master/app/src/main/java/com/gabo/ramo/domain)  All the business logic happens in this layer. You find all the `Interactor`s here. In this case, interactors meet the `S` letter in the `SOLID principle`, `Single Responsibility`, that means each interactor in the domain layer is doing only one thing at a time. So, that has just a single responsibility that improves the maintainability when any changes over time include a few specific classes reducing side effects in other features/responsibilities.
3. [Presentation layer:](https://github.com/llanox/AndroidMVPCoroutines/tree/master/app/src/main/java/com/gabo/ramo/presentation) Here lives the Model-View-Presenter (MVP) pattern, where the `View` is an Activity or a Fragment, the `Model` is how we share/get data with domain layer. `Presenter` is the man-in-the-middle that handles user actions from the `View`, then consumes data through `Interactor`s and finally passes the data to rendering it to the `View`.
