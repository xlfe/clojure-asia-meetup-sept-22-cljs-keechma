(ns demo.pages.welcome
  (:require                                                                                                                                                

    [keechma.next.helix.core :refer [use-sub dispatch]]
    [demo.helix :refer [defnc]]
    [helix.core :as hx :refer [$ <>]]
    [keechma.next.controllers.router :as router]
    ["@mui/material/Typography" :default Typography]
    ["@mui/material/Paper" :default Paper]
    ["@mui/material/IconButton" :default IconButton]
    ["@mui/material/Grid" :default Grid]
    ["@mui/icons-material/Delete" :default Delete]
    ["@mui/icons-material/Edit" :default Edit]))

(defnc text-block
  [{:keys [children title id] :as props}]
  ($ Paper
    {:sx #js{:mt 2 ;margin-top
             :p 2}};padding}}
    ($ Grid
      {:container true
       :alignItems "center"
       :sx #js{:mb 2}}
      ($ Typography 
        {:variant "h4"
         :sx #js{:flexGrow "1"}} 
        title)
      ($ IconButton
        {:onClick #(router/redirect! props :router {:page "edit" :id id})}
        ($ Edit))
      ($ IconButton
        {:onClick #(dispatch props :app :remove id)}
        ($ Delete)))
    ($ Typography children)))
    
(defnc render
  [props]
  (let [{:keys [paragraphs]} (use-sub props :app)]
    (<>
      ($ Typography 
        {:variant "h2"}
        "Welcome 👋")

      ($ Typography
        {:variant "h4"}
        (str "Here are your " (count paragraphs) " paragraphs"))
      (map
        (fn [[id {:keys [title text]}]]
          ($ text-block 
             ; note for lists, you should provide a unique key for the list entry, or MuI complains
             {:key id 
              :title title
              :id id
              & props}
             text))
        paragraphs))))
      

