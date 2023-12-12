import styles from "./Button.module.css"

export default function Button({ primary, disabled, onClick, children }) {
  const classNames = [styles.Button]
  if (disabled) {
    classNames.push(styles.disabled)
  } else if (primary) {
    classNames.push(styles.primary)
  }

  return <button className={classNames.join(' ')} onClick={(e) => !disabled && onClick(e)}>{children}</button>
}