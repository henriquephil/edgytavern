import Button from "../../../../components/Button"
import FormInput from "../../../../components/FormInput"

function SpotGroup({ spot }) {
  return (
    <div className={SpotGroup}>
      <FormInput label="Group name">
        <input type="text" value={spot.name} disabled/>
      </FormInput>
      <FormInput label="Amount of spots">
        <input type="text" value={spot.amount} disabled/>
      </FormInput>
      <FormInput>
        <Button onClick={() => console.log(spot)}>
          Delete
        </Button>
      </FormInput>
    </div>
  )
}

export default SpotGroup
