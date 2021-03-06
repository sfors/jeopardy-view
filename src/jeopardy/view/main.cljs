(ns ^:figwheel-hooks jeopardy.view.main
  (:require
    [goog.dom :as gdom]
    [reagent.dom :as rdom]
    [re-frame.core :as re-frame]
    [jeopardy.view.events :as events]
    [jeopardy.view.subs :as subs]
    [jeopardy.view.components.service-button :refer [call-service]]))

;; define your app data so that it doesn't get over-written on reload
(defn get-app-element []
  (gdom/getElement "app"))

(defn flip-card
[value question flipped]
  [:td {:style { :color "#DDD" }}
   [:div {:style { :background-color "transparent" :perspective "1000px" :width "100%" :height "49px"}}
    [:div {:style { :width "100%" :height "100%" :position "relative" :transition "transform 0.8s" :transform-style "preserve-3d" :transform (if flipped "rotateY(180deg)" "")}}
     [:div {:style {:border-radius "3px" :background "linear-gradient(145deg, rgba(100,100,200,1) 0%, rgba(0,0,200,1) 100%)" :position "absolute" :width "100%" :height "100%" :-webkit-backface-visibility "hidden" :backface-visibility "hidden"}} value]
     [:div {:style {:border-radius "3px" :background "linear-gradient(145deg, rgba(100,100,200,1) 0%, rgba(0,0,200,1) 100%)" :position "absolute" :width "100%" :height "100%" :-webkit-backface-visibility "hidden" :backface-visibility "hidden" :transform "rotateY(180deg)"}} question]
     ]]])

(defn flip-card-new
  [value question flipped]
  [:div {:style { :color "#DDD" :flex-grow 1}}
   [:div {:style { :background-color "transparent" :perspective "1000px" :width "100%" :height "49px"}}
    [:div {:style { :width "100%" :height "100%" :position "relative" :transition "transform 0.8s" :transform-style "preserve-3d" :transform (if flipped "rotateY(180deg)" "")}}
     [:div {:style {:border-radius "3px" :background "linear-gradient(145deg, rgba(100,100,200,1) 0%, rgba(0,0,200,1) 100%)" :position "absolute" :width "100%" :height "100%" :-webkit-backface-visibility "hidden" :backface-visibility "hidden"}} value]
     [:div {:style {:border-radius "3px" :background "linear-gradient(145deg, rgba(100,100,200,1) 0%, rgba(0,0,200,1) 100%)" :position "absolute" :width "100%" :height "100%" :-webkit-backface-visibility "hidden" :backface-visibility "hidden" :transform "rotateY(180deg)"}} question]
     ]]])

(defn app-component
  []
  (let [clicks (deref (re-frame/subscribe [::subs/clicks]))
        username (deref (re-frame/subscribe [::subs/username]))
        flipped (= (mod clicks 2) 1)
        board {:board
               {:categories
                [{:title "kings",
                  :id 1,
                  :cards
                         [{:points 100,
                           :answer "Carl gustav",
                           :question "What king?",
                           :flipped? false}
                          {:points 200,
                           :answer "Carl gustav",
                           :question "What king?",
                           :flipped? false}
                          {:points 300,
                           :answer "Carl gustav",
                           :question "What king?",
                           :flipped? false}]}
                 {:title "programming",
                  :id 2,
                  :cards
                         [{:points 100,
                           :answer "Carl gustav",
                           :question "What king?",
                           :flipped? false}
                          {:points 200,
                           :answer "Carl gustav",
                           :question "What king?",
                           :flipped? false}
                          {:points 300,
                           :answer "Carl gustav",
                           :question "What king?",
                           :flipped? false}]}
                 {:title "animals",
                  :id 3,
                  :cards
                         [{:points 100,
                           :answer "Carl gustav",
                           :question "What king?",
                           :flipped? false}
                          {:points 200,
                           :answer "Carl gustav",
                           :question "What king?",
                           :flipped? false}
                          {:points 300,
                           :answer "Carl gustav",
                           :question "What king?",
                           :flipped? false}]}]}
               }

        tdStyle {:border-radius "3px" :padding "1rem" :color "#DDD" :background "linear-gradient(145deg, rgba(100,100,200,1) 0%, rgba(0,0,200,1) 100%)"}
        tdStyle-new {:border-radius "3px" :padding "1rem" :color "#DDD" :background "linear-gradient(145deg, rgba(100,100,200,1) 0%, rgba(0,0,200,1) 100%)" :flex-grow 1}]
    [:div
     [:h1 "Jeopardy"]
     [:div
      [:span "Username"]
      [:input {:on-change (fn [e] (re-frame/dispatch [::events/username-changed (-> e (.-target) (.-value))]))
               :value username}]
      [:button {:on-click (fn [] (re-frame/dispatch [::events/join-game]))}
       "Join game!"]]
     [:p username]
     [:div {:style {:display "flex" }}
      (map (fn [category] [:div {:style {:flex-grow 1}}
                           [:div {:style tdStyle-new} (:title category)]
                           (map (fn [card]
                                  [flip-card-new (:points card) (:question card) flipped]
                                  )
                                (:cards category))
                           ]) (:categories (:board board)))
      ]

     [:table {:style {:border-radius "5px" :background-color "black" :text-align "center"} :cellSpacing "3px"}
      [:thead
       [:tr (map (fn [category] [:th {:style tdStyle} (:title category)]) (:categories (:board board)))]]
      [:tbody
       ;(map (fn [board] ))
       [:tr [flip-card "200" "Fr??gan" flipped] [flip-card "200" "Fr??gan" flipped] [flip-card "200" "Fr??gan" flipped]]
       [:tr [flip-card "400" "Fr??gan" flipped] [flip-card "400" "Fr??gan" flipped] [flip-card "400" "Fr??gan" flipped]]
       [:tr [flip-card "600" "Fr??gan" flipped] [flip-card "600" "Fr??gan" flipped] [flip-card "600" "Fr??gan" flipped]]
       [:tr [flip-card "800" "Fr??gan" flipped] [flip-card "800" "Fr??gan" flipped] [flip-card "800" "Fr??gan" flipped]]
       [:tr [flip-card "1000" "Fr??gan" flipped] [flip-card "1000" "Fr??gan" flipped] [flip-card "1000" "Fr??gan" flipped]]]]
    [:p {:style {:color "red"}} (str "Clicks: " clicks)]
    [:button
     {:on-click (fn []
                  (re-frame/dispatch [::events/button-clicked]))}
     "Click me!"]
     [call-service]]))


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

(when (= (deref re-frame.db/app-db) {})
  (init))
