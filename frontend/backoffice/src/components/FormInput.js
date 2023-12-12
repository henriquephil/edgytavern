export default function FormInput({ w, grow, label, top, children }) {
  const style = {
    flexBasis: `${w}%`
  }
  if (grow)
    if (grow >= 0)
      style.flexGrow = grow
    else
      style.flexShrink = -grow
  if (top)
    style.justifyContent = 'flex-start'

  return (
    <div style={style} className="form-input">
        { label ? <label>{label}</label> : null }
        { children }
    </div>
  )
}
