# MVP Pattern Architecture using Coroutines

It's an Android project with Kotlin implementing/exploring an MVP architectural pattern where the network calls are using  Coroutines in the Data layer with the Repository pattern. 
The Coroutines allow us to keep code more clear and concise. However, I don't know yet how to implement that data response processing that is easy to do with some functions with RxJava, that's something for a further version.

## Architecture Diagram
![App architecture](/images/architecture_android.png)

1. [Data layer:](https://github.com/llanox/AndroidMVPCoroutines/tree/master/app/src/main/java/com/gabo/ramo/data) All data needed for the application comes from this layer using the Repository pattern. Here, we cna find the Caching Strategy logic.
2. [Domain layer:](https://github.com/llanox/AndroidMVPCoroutines/tree/master/app/src/main/java/com/gabo/ramo/domain) All the business logic happens in this layer. You will find all the `Interactor`s here.
3. [Presentation layer:](https://github.com/llanox/AndroidMVPCoroutines/tree/master/app/src/main/java/com/gabo/ramo/presentation) Here lives the Model-View-Presenter (MVP) pattern, where the `View` is an Activity or a Fragment, the `Model` is shared with the domain layer `Presenter` is the man-in-the-middle that handles user actions from the `View`, then consumes data through `Interactor`s and finally passes the data to be rendered to the `View`.
