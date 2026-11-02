(ns omingard-css.core
  (:require [garden.def :refer [defstylesheet defstyles]]
            [garden.units :refer [px]]
            [garden.stylesheet :refer [at-media]]
            [garden.units :as u :refer [px pt rem px+ px* px- px-div rem-div]])
  (:refer-clojure :exclude [rem])) ;; `rem` is Clojure core's remainder function

(def content-width (px 1000))
(def card-width "10%")
(def padding-value 1)
(def padding (rem padding-value))
(def half-padding (rem (/ padding-value 2)))

(defstylesheet application
  {:output-to "resources/public/application.css"}

  ;; http://nicolasgallagher.com/micro-clearfix-hack/ */
  (comment
    For modern browsers
    1. The space content is one way to avoid an Opera bug when the
       contenteditable attribute is included anywhere else in the document.
       Otherwise it causes space to appear at the top and bottom of elements
       that are clearfixed.
    2. The use of `table` rather than `block` is only necessary if using
       `:before` to contain the top-margins of child elements.)

  [:.cf:before :.cf:after
     {:content (pr-str " ")
      :display "table"
     }]

  [:.cf:after {:clear "both"}]

  ;; For IE 6/7 only
  ;; Include this rule to trigger hasLayout and contain floats.
  [:.cf {:*zoom 1}]

  [:* {
    :box-sizing "border-box"}]

  [:html {}
    (map
      (fn [[min-width font-size]]
        (at-media {:min-width min-width}
          [:& {:font-size font-size}]))
      [[   "0px" "30.00%"]
       [ "320px" "40.00%"]
       [ "768px" "50.06%"]
       ["1024px" "58.74%"]
       ["1200px" "62.50%"]
       ["1600px" "83.03%"]])]

  [:body {
    :font-family "'Droid Sans', sans-serif"
    :font-size (rem 1.6)
    :-webkit-touch-callout "none"
    :-webkit-user-select "none"
    :-khtml-user-select "none"
    :-moz-user-select "none"
    :-ms-user-select "none"
    :user-select "none"}]

  [:.omingard-wrapper {
    :outline "0"
   }]

  [:.black
    {:color "black"}]
  [:.red
    {:color "red"}]

  [:.l-navigation-container {
    :width "100%"
    :padding "0 1rem"
    :background "black"}]

  ;; Flex + nowrap so the buttons can never wrap down over the cards,
  ;; and a 44px floor because rem-based heights get too small to tap on phones.
  [:.m-navigation {
    :display "flex"
    :flex-wrap "nowrap"
    :align-items "stretch"
    :min-height (px 44)
    :height (rem 4.5)
    :list-style "none"
    :padding "0"
    :color "#eee"
    :margin "0 auto"}
    (at-media {:min-width content-width}
      [:& {:width content-width}])]

  [:.m-navigation--item
    {:display "flex"
     :align-items "center"
     :flex "0 0 auto"
     :margin-left (rem 1)
     :white-space "nowrap"}

    [:&.as-title
      {:flex "1 1 auto"
       :margin-left 0
       :overflow "hidden"}]
    [:a
      {:color "white"
       :text-decoration "none"}]
  ]

  [:.m-navigation--title {
    :font-size "2rem"
    :margin "0"}]

  [:.m-navigation--new-game :.m-navigation--undo {
    :align-self "stretch"
    :min-width (rem 10)
    :padding "0 1rem"
    :white-space "nowrap"
    :touch-action "manipulation"
    :cursor "pointer"}]

  [:.m-navigation--new-game {
    :color "#bbb"
    :background "#888"
    :border "1px solid #888"}]

  [:.m-navigation--undo {
    :color "white"
    :background "#444"
    :border "1px solid #444"}]

  [:.l-game-container
    {:width "100%"
     :margin "0 auto"
     :padding 0
     :touch-action "manipulation"
     :-webkit-touch-callout "none"
     :-webkit-user-select "none"
     :-khtml-user-select "none"
     :-moz-user-select "none"
     :-ms-user-select "none"
     :user-select "none" ;; disable text selection on cards
     }
     (at-media {:min-width content-width}
       [:& {:width content-width}])]

  [:.m-columns-wrapper {
    :margin "0 auto"
    :padding-left padding
    :padding-right padding}]

  [:.m-columns {
    :margin 0
    :padding 0}]

  [:.m-column-wrapper {
    :float "left"
    :width card-width
    :margin-right "1%"}]

   [:.m-column {
     :list-style "none"
     :padding 0}]

   [:.m-column--placeholder {
     :height (px 25)
     :border-radius (px 3)
     :border "1px dashed #ddd"
     :margin "2px auto"
     :background-color "#fafafa"
     :color "#999"
     :font-size (px 13)
     :text-align "center"
     :line-height (rem 2)}]

  [:.m-card
     {:background "#ddd"
      :line-height (px 11)
      :font-size (rem 2.4)
      :margin "2px 0"
      :padding "5px 0"
      :text-align "center"
      :border-radius (px 3)
      :height (px 25)
      :border "1px solid #bbb"
      :white-space "nowrap"
      :overflow "hidden"
     }
    (at-media {:max-width (px 500)}
      [:& {:font-size (rem 1.95)
           :padding "6px 0"}])
    (at-media {:min-width (px 700)}
      [:& {:font-size (rem 2.3)}])
    ;; Cards stop widening at content-width but rem keeps growing, so cap it
    ;; before the glyphs outgrow the fixed 25px card height.
    (at-media {:min-width content-width}
      [:& {:font-size (px 18)}])
       [:&.as-open
         {:background-color "#fafafa"
          :cursor "pointer"}]
       [:&.as-moving
         {:background-color "rgba(255, 255, 0, 0.5)"}]
       [:&.is-moveable
         {:border "1px solid rgba(0, 200, 0, 0.5)"
          :box-shadow "0px 0px 3px rgba(0, 200, 0, 1)"}]
       [:&.as-closed
         {:background-color "#444"
          :background-image "url(card_back.png)"
          :background-position "center 72px"}]]

  [:.m-card_small
    {:background "#fafafa"
     :padding "0 3px"
     :border "1px solid #bbb"
     :border-radius (px 3)}]

  [:.l-piles-container
    {:margin-bottom padding
     :padding padding
     :background-color "#dbdbdb"
     :touch-action "manipulation"
     :-webkit-touch-callout "none"
     :-webkit-user-select "none"
     :-khtml-user-select "none"
     :-moz-user-select "none"
     :-ms-user-select "none"
     :user-select "none" ;; disable text selection on cards
     }
    ]

  [:.l-piles-container--headline
    {:float "left"
     :margin "0"}]

  [:.l-piles-container--note
    {:font-size (rem 1.2)
     :font-weight "normal"
     :margin-left (rem 1)
     :color "#555"}]

  [:.l-piles-container--new-round
     {:background-color "#234892"
      :color "#fff"
      :border "1px solid #234892"
      :width "100%"
      :height (px 25)
      :line-height (rem 1.4)
      :border-radius (px 3)
      :margin "2px 0"
      }]

  [:.m-piles
    {:padding (px 0)
     :list-style-type "none"
     :float "left"
     :width "100%"
     :margin-top half-padding
     :margin-bottom half-padding}]

  [:.m-pile {
    :float "left"
    :margin-right "1%"
    :width "10%"}]

  [:.m-pile--placeholder
    {:background "#fafafa"
      :line-height (rem 1)
      :margin "2px 0"
      :padding "5px"
      :text-align "center"
      :border-radius (px 3)
      :height (px 25)
      :border "1px solid #bbb"
      :cursor "pointer"}
    [:&.red {
      :color "rgba(255, 0, 0, 0.5)"}]
    [:&.black {
      :color "rgba(0, 0, 0, 0.5)"}]]

  [:.l-howto
    (let [width 50]
      {:display "none"
       :background "rgba(204, 204, 204, 0.9)"
       :color "#333"
       :position "fixed"
       :left "50%"
       :top "70%"
       :width (str width "%")
       :margin (str "-140px 0 0 " (- (/ width 2)) "%")
       :padding (px 10)
       :border-radius (px 3)
       :box-shadow "2px 2px 5px rgba(0, 0, 0, 0.4)"})
    [:&.is-visible
      {:display "block"}]
    [:&:after
       {:content (pr-str "x")
        :position "absolute"
        :top (px 5)
        :right (px 10)
        :cursor "pointer"
        :color "#eee"
        :background-color "#333"
        :border-radius "100%"
        :width (px 20)
        :height (px 20)
        :text-align "center"
        :font-size (px 15)
        }]
    [:h1
      {:margin-top 0}]
    [:ol
      {:padding-left (px 20)}]

   ]

  [:html {:color-scheme "light dark"}]

  [:body {:font-variant-emoji "text"}]

  (at-media {:prefers-color-scheme "dark"}
    [:body
      {:background "#1a1a1a"
       :color "#ddd"}]

    [:.m-column--placeholder
      {:border-color "#444"
       :background-color "#222"
       :color "#777"}]

    ;; Card faces stay light so black suits keep their contrast.
    [:.m-card
      {:background "#333"
       :border-color "#555"}]
    [:.m-card.as-open
      {:background-color "#c4c4c4"
       :border-color "#888"}]
    [:.m-card.as-moving
      {:background-color "#d9cf5e"}]
    [:.m-card.as-closed
      {:background-color "#444"
       :filter "brightness(0.7)"}]

    [:.m-card_small
      {:background "#c4c4c4"
       :border-color "#888"}]

    [:.l-piles-container
      {:background-color "#262626"}]
    [:.l-piles-container--note
      {:color "#999"}]

    [:.m-pile--placeholder
      {:background "#c4c4c4"
       :border-color "#888"}]

    [:.l-howto
      {:background "rgba(45, 45, 45, 0.95)"
       :color "#ddd"
       :box-shadow "2px 2px 8px rgba(0, 0, 0, 0.8)"}]
    [:.l-howto:after
      {:color "#222"
       :background-color "#ddd"}])
)
