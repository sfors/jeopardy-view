(ns ^:figwheel-hooks jeopardy.view.main
  (:require
   [goog.dom :as gdom]
   [reagent.dom :as rdom]
   [re-frame.core :as re-frame]
   [jeopardy.view.events :as events]
   [jeopardy.view.subs :as subs]))

;; define your app data so that it doesn't get over-written on reload
(defn get-app-element []
  (gdom/getElement "app"))

(defn app-component
  []
  (let [clicks (deref (re-frame/subscribe [::subs/clicks]))]
    [:div
     [:h1 "Hej!"]
     [:p {:style {:color "red"}} (str "Clicks: " clicks)]
     [:button
      {:on-click (fn []
                   (re-frame/dispatch [::events/button-clicked]))}
      "Click me!"]]))


(defn mount [el]
  (rdom/render [app-component] el))

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (mount el)))

;; conditionally start your application based on the presence of an "app" element
;; this is particularly helpful for testing this ns without launching the app
(mount-app-element)

;; specify reload hook with ^;after-load metadata
(defn on-reload
  {:after-load true}
  []
  (mount-app-element)
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )


(defn init []
  (re-frame/dispatch [::events/initialize-db]))


(init)
