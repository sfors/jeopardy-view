(ns jeopardy.view.components.service-button
  (:require [re-frame.core :as re-frame]
            [jeopardy.view.events :as events]
            [jeopardy.view.subs :as subs]))

(defn call-service
  []
  (let [service-response (deref (re-frame/subscribe [::subs/service-response]))]
    [:div
     [:button {:on-click
               (fn [] (re-frame/dispatch [::events/call-service]))}
      "Call service!"]
     (if service-response
       [:p (str service-response)]
       [:p "No response!"])
     ]))
