(ns demo.app
  (:require                                                                                                                                                

    [demo.pages :as pages]
    [demo.pages.welcome :as welcome]
    [demo.pages.info :as info]

    [keechma.next.core :as keechma]
    [keechma.next.helix.core :refer [KeechmaRoot]]
    [keechma.next.controller :as ctrl]
    [keechma.next.controllers.router :as router]

    [helix.core :as hx :refer [$]]

    ["react" :as react]
    ["react-dom/client" :as rdom]

    [taoensso.timbre :as timbre :refer-macros [debug info warn error]]))


(def li 
  {1 {:title "First" :text "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse iaculis euismod ligula at ultrices. Morbi varius nec nisi vitae lobortis. Mauris vestibulum elit sit amet metus vestibulum volutpat. Donec eu facilisis tortor. Aliquam lacinia dolor non justo congue interdum. Nam turpis magna, congue vel faucibus sed, lobortis nec risus. Aenean sed ipsum vehicula, commodo purus ac, facilisis velit. Etiam suscipit ex ex, et euismod libero cursus at. Praesent ac sagittis ipsum. Morbi in commodo quam. Donec massa libero, tincidunt eu nunc vel, ullamcorper elementum tellus. Vestibulum at porta lacus. Sed id fermentum turpis, et euismod nibh. Donec rutrum nulla id nunc facilisis lacinia. Phasellus in dui porta, laoreet tellus sollicitudin, laoreet nibh. Proin vulputate nec ex eget viverra."} 
   2 {:title "Second" 
      :text "Morbi viverra hendrerit finibus. Nunc libero enim, condimentum et tincidunt vitae, viverra id est. Aenean efficitur nisi a nibh tempus, sed laoreet dui ullamcorper. Nulla vitae lacinia risus. Vivamus lacus ante, ullamcorper non sapien et, volutpat condimentum augue. Donec rhoncus a elit condimentum suscipit. Etiam nec diam ut metus tristique porta ut sit amet felis. In et fringilla quam. Phasellus a libero eget ex convallis tincidunt. Fusce feugiat tempus tellus, a commodo metus fringilla a. Integer non ultricies eros, eu vulputate odio. Nunc laoreet libero id gravida elementum. Nullam malesuada bibendum mauris a tempor. Ut maximus aliquam aliquam. Sed in nisl eget nulla rhoncus vehicula et eu orci."} 
   3 {:title "Third" 
      :text "Aliquam condimentum est ligula, eu interdum arcu ultricies vitae. Pellentesque porttitor tellus odio, ac porta diam varius at. Fusce dictum tempor mauris, non egestas mauris scelerisque vel. Fusce lobortis porttitor tortor, eget elementum augue placerat eget. Morbi libero lacus, varius non laoreet vel, laoreet eu nulla. Sed massa purus, rutrum et commodo eu, eleifend quis diam. Suspendisse sapien mi, condimentum ac tempor vulputate, dignissim nec ligula. Vivamus finibus nulla et lacus tristique, sit amet euismod est ultrices. Fusce libero erat, porta sed ullamcorper sit amet, laoreet sit amet sapien. Donec a accumsan eros. In hac habitasse platea dictumst. Vivamus efficitur placerat nisl at cursus. Vestibulum vehicula varius libero vel pellentesque. Pellentesque auctor lectus nec convallis euismod. Pellentesque sapien odio, aliquam non sagittis et, ullamcorper vitae nunc."} 
   4 {:title "Fourth" 
      :text "Vestibulum ut dapibus ante. Integer pellentesque fermentum nunc eu tempor. Integer laoreet gravida dolor, at sodales mauris gravida a. Curabitur augue lorem, auctor id aliquam sed, mollis interdum ipsum. Curabitur nisi orci, suscipit et sem quis, blandit mollis tellus. Proin tincidunt quis libero a vehicula. Sed auctor molestie elit et facilisis. Morbi scelerisque quam eget nisi maximus, in posuere odio finibus. Nam eget maximus lacus. Morbi tellus eros, semper at faucibus eu, elementum quis leo. Curabitur vehicula consectetur egestas. Nullam pellentesque quis purus quis pharetra. Integer libero sem, congue ut mollis eu, tempus a est."} 
   5 {:title "Fifth" 
      :text "Donec facilisis volutpat auctor. Suspendisse venenatis nunc et ultricies luctus. Vestibulum erat est, gravida a volutpat quis, tincidunt vel lacus. Nunc elementum eros et massa congue sollicitudin. Praesent libero felis, fermentum quis pretium sed, consequat nec magna. Vivamus sed sollicitudin ligula. Sed malesuada urna at quam semper lacinia eu ut ligula. Donec sodales libero nulla, eget accumsan nisi rutrum et. Vivamus a condimentum enim, eu gravida est. Ut vitae egestas erat, sit amet viverra turpis. Fusce iaculis quam id sapien efficitur, sit amet blandit lacus aliquam. Donec nunc justo, rutrum et consectetur id, interdum at sapien. Vivamus non tincidunt elit. Duis ante turpis, sagittis id mollis nec, condimentum in lorem. Pellentesque eget suscipit mauris. Donec ultrices finibus venenatis."}})

(def app-defn {:keechma/controllers 
                 {
                  :router 
                  #:keechma.controller{:params true
                                       :type :keechma/router
                                       :keechma/routes [["" {:page welcome/render}]
                                                        ["edit/:id" {:page info/render}]]}
                  :app
                  {:keechma.controller/params true}}})


; Keechma controller - :app
; Application controller


(derive :app :keechma/controller)

(defmethod ctrl/handle :app [{:as ctrl :keys [state*]} event v]
  (debug event)

  (case event
    :remove (swap! state* update :paragraphs #(dissoc % v))
    :page   (swap! state* assoc :page v)
    nil))


(defmethod ctrl/start :app [ctrl params deps-state prev-state]

  {:page :home
   :paragraphs li})
                        
;
; Application 
; 

(defonce app-instance* (atom nil))
(defonce app-root* (atom nil))

;; start is called by init and after code reloading finishes
(defn start 
  []
  (let [app-instance (or @app-instance*
                         (reset! app-instance* (keechma/start! app-defn)))
        app-root (or @app-root*
                    (reset! app-root* (rdom/createRoot (js/document.getElementById "app"))))]
    (debug "mounting app")
    (.render 
      app-root
      ($ react/StrictMode ($ KeechmaRoot {:keechma/app app-instance} ($ pages/main-))))))

(defn init []
  ;; init is called ONCE when the page loads
  ;; this is called in the index.html and must be exported
  ;; so it is available even in :advanced release builds
  (debug "init application")
  (start)
  (set! (.-title js/document) "My Demo App"))

