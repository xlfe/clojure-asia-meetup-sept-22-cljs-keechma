(ns demo.appbar
  (:require 

    [demo.helix :refer [defnc]]
    [helix.core :as hx :refer [$]]

    ["@mui/material/Box" :default Box]
    ["@mui/material/CircularProgress" :default CircularProgress]
    ["@mui/material/useScrollTrigger" :default useScrollTrigger]
    ["@mui/material/Slide" :default Slide]
    ["@mui/material/AppBar" :default AppBar]
    ["@mui/material/Toolbar" :default Toolbar]
    ["@mui/material/Typography" :default Typography]))
         
(defnc render
  [props]
  (let [trigger (useScrollTrigger #js{:threshold 20 :disableHysteresis true})]
    ($ Slide                                                                                                                                             
      {:in (not trigger)}
      ($ AppBar
        {:sx #js {:position "sticky"}}
        ($ Toolbar
          ($ Box {:display "inline-block" 
                  :sx #js{:flexGrow "1" 
                          :mr 2}}  ;mr is shorthand for margin-right
            ($ Typography  
              {:variant "h6"
               :aria-label "application title"} 
              "Demo App"))
          ($ CircularProgress
            {:color "secondary"}))))))
