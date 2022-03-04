package ca.sudbury.hojat.roomdemo

/**
 * Created by Hojat Ghasemi at 2022-03-04
 * Contact the author at "https://github.com/hojat72elect"
 *
 * This object is intended for simple communications between View and ViewModel.
 */

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}
